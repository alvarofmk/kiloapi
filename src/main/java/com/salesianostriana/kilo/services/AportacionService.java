package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AportacionService {

    private final AportacionRepository aportacionRepository;

    private final TipoAlimentoRepository tipoAlimentoRepository;


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

    /*public Optional<Aportacion> editAportacion(){

    }*/

}
