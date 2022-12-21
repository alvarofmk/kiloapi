package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionRequestDTO;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.dtos.aportaciones.LineaDTO;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.repositories.KilosDisponiblesRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AportacionService {

    private final AportacionRepository aportacionRepository;

    private final TipoAlimentoRepository tipoAlimentoRepository;

    private final ClaseService claseService;

    private final KilosDisponiblesRepository kilosDisponiblesRepository;


    public Optional<Aportacion> findById(Long id){ return aportacionRepository.findById(id); }

    public List<Aportacion> findAll() {
        return aportacionRepository.findAll();
    }

    public boolean tipoAlimentoInAportacion(Long id) {
        List<Integer> lista = aportacionRepository.tipoAlimentoInAportacion(id);
        return lista
                .stream()
                .anyMatch(num -> num == 1);
    }

    public List<AportacionesReponseDTO> findAllAportaciones() {
        return aportacionRepository.getAllAportaciones();
    }

    public List<AportacionesReponseDTO> findAllAportacionesByClase(Long id) {
        List<DetallesAportacionResponseDTO> detalles = aportacionRepository.getAllDetalleAportacion(id);
        List<AportacionesReponseDTO> lista = aportacionRepository.getAllAportacionesOfClass(id);
        lista.forEach(a-> a.setPares(detalles, a.getId()));
        return lista;
    }

    public void deleteLinea(Long id, Long numLinea){
        Optional<Aportacion> aportacion = aportacionRepository.findById(id);

        if(aportacion.isPresent()){
            Aportacion a = aportacion.get();
            Optional<DetalleAportacion> detalles = buscarLinea(numLinea, a.getDetalleAportaciones());

            if(detalles.isPresent()){
                DetalleAportacion detalle = detalles.get();
                borrarDetalle(detalle, a);
            }
        }
    }

    public Optional<DetalleAportacion> buscarLinea(Long numLinea, List<DetalleAportacion> detalles ){
        return detalles
                .stream()
                .filter(d -> d.getDetalleAportacionPK().getLineaId() == numLinea)
                .findFirst();
    }

    public void borrarDetalle(DetalleAportacion detalle, Aportacion a){
        if(aportacionRepository.findDetallesBorrables().contains(detalle)){

            detalle
                    .getTipoAlimento()
                    .getKilosDisponibles()
                    .setCantidadDisponible(
                            (double) Math.round((detalle.getTipoAlimento().getKilosDisponibles().getCantidadDisponible() - detalle.getCantidadKg())* 100d) /100d
                    );
            a.removeDetalleAportacion(detalle);
            tipoAlimentoRepository.save(detalle.getTipoAlimento());
        }
    }

    public void borrarDetallesAportacion(Aportacion a){
        Iterator<DetalleAportacion> it = a.getDetalleAportaciones().iterator();

        while(it.hasNext()){
            DetalleAportacion detalle = it.next();
            if(aportacionRepository.findDetallesBorrables().contains(detalle)){

                detalle
                        .getTipoAlimento()
                        .getKilosDisponibles()
                        .setCantidadDisponible(
                                (double) Math.round((detalle.getTipoAlimento().getKilosDisponibles().getCantidadDisponible() - detalle.getCantidadKg())* 100d) /100d
                        );

                tipoAlimentoRepository.save(detalle.getTipoAlimento());
                it.remove();
            }
        }
        aportacionRepository.save(a);
    }

    public void deleteAportacionById(Long id){

        Optional<Aportacion> a = aportacionRepository.findById(id);

        if(a.isPresent()){
            Aportacion aportacion = a.get();
            borrarDetallesAportacion(aportacion);

            if(aportacion.getDetalleAportaciones().isEmpty())
                aportacionRepository.deleteById(id);
        }
    }

    public Optional<AportacionesReponseDTO> createAportacion(AportacionRequestDTO dto) {

        if(dto.getLineas() != null &&
            !dto.getLineas().isEmpty() &&
            dto.getIdClase() != null &&
            claseService.existsById(dto.getIdClase())) {
            Optional<Clase> clase = claseService.findById(dto.getIdClase());
            Aportacion creada = Aportacion
                                    .builder()
                                    .fecha(LocalDate.now())
                                    .clase(clase.get())
                                    .build();

            List<LineaDTO> lineas = dto.getLineas();
            if(lineas.stream().allMatch(l -> l.getIdTipo() != null &&
                                                l.getKg() != null &&
                                                l.getKg() > 0 &&
                                                tipoAlimentoRepository.existsById(l.getIdTipo()))) {
                AtomicReference<Long> contador = new AtomicReference<>(1L);
                lineas.forEach(l -> {
                    Optional<TipoAlimento> tipo = tipoAlimentoRepository.findById(l.getIdTipo());
                    if (tipo.isPresent()) {
                        DetalleAportacionPK pk = new DetalleAportacionPK(contador.get(), creada.getId());
                        DetalleAportacion linea = DetalleAportacion
                                .builder()
                                .detalleAportacionPK(pk)
                                .tipoAlimento(tipo.get())
                                .cantidadKg(l.getKg())
                                .build();
                        contador.set(contador.get() + 1L);
                        creada.addDetalleAportacion(linea);
                        KilosDisponibles kilos = kilosDisponiblesRepository.findById(l.getIdTipo()).get();
                        kilos.setCantidadDisponible(kilos.getCantidadDisponible() + l.getKg());
                        kilos.addTipoAlimento(tipo.get());
                        kilosDisponiblesRepository.save(kilos);
                    }

                });
            }else {
                return Optional.empty();
            }
            return Optional.of(AportacionesReponseDTO.of(aportacionRepository.save(creada)));
        }else {
            return Optional.empty();
        }

    }

}
