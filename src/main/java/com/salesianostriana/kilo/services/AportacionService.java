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
    private final TipoAlimentoSaveService tipoAlimentoSaveService;

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
            tipoAlimentoSaveService.save(detalle.getTipoAlimento());
        }
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

                tipoAlimentoSaveService.save(detalle.getTipoAlimento());
                it.remove();
            }
        }
        aportacionRepository.save(a);
    }

    public Optional<Aportacion> editAportacion(Long idAportacion, Long numLinea, double kg){

        Optional<Aportacion> a = aportacionRepository.findById(idAportacion);

        if(a.isPresent() && kg>0){
            Optional<DetalleAportacion> detalle =
                    a.get().getDetalleAportaciones().size() >= numLinea ?
                            Optional.of(a.get().getDetalleAportaciones().get(numLinea.intValue() -1))
                            :
                            Optional.empty();

            if(detalle.isPresent())
                return cambiarKilosDetalle(detalle.get(), kg);
        }
        return Optional.empty();
    }

    public Optional<Aportacion> cambiarKilosDetalle(DetalleAportacion detalle, double kgNuevos){

        double result = kgNuevos - detalle.getCantidadKg();
        double kilosActuales = detalle.getTipoAlimento().getKilosDisponibles().getCantidadDisponible();
        double kilosNuevos = (double) Math.round((kilosActuales + result) *100d) /100d;

        if(result < 0){
            if(kilosActuales < result*-1)
                return Optional.empty();
            else{
                detalle.getTipoAlimento().getKilosDisponibles().setCantidadDisponible(
                        kilosNuevos
                );
                detalle.setCantidadKg(kgNuevos);
                tipoAlimentoSaveService.save(detalle.getTipoAlimento());
                return Optional.of(aportacionRepository.save(detalle.getAportacion()));
            }
        }
        else{
            detalle.getTipoAlimento().getKilosDisponibles().setCantidadDisponible(
                    kilosNuevos
            );
            detalle.setCantidadKg(kgNuevos);
            tipoAlimentoSaveService.save(detalle.getTipoAlimento());
            return Optional.of(aportacionRepository.save(detalle.getAportacion()));
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
                                                tipoAlimentoSaveService.existsById(l.getIdTipo()))) {
                AtomicReference<Long> contador = new AtomicReference<>(1L);
                lineas.forEach(l -> {
                    Optional<TipoAlimento> tipo = tipoAlimentoSaveService.findById(l.getIdTipo());
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

                        kilos.setCantidadDisponible((double)Math.round((kilos.getCantidadDisponible() + l.getKg())*100)/100);

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
