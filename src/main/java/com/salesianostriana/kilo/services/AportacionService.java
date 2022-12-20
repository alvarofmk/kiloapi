package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.repositories.KilosDisponiblesRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void deleteLinea(Long id, Long numLinea){
        Optional<Aportacion> aportacion = aportacionRepository.findById(id);

        if(aportacion.isPresent()){
            Aportacion a = aportacion.get();
            Optional<DetalleAportacion> detalles = buscarLinea(numLinea, a.getDetalleAportaciones());

            if(!detalles.isEmpty()){
                DetalleAportacion detalle = detalles.get();


                    if(detalle.getCantidadKg()<= detalle.getTipoAlimento().getKilosDisponibles().getCantidadDisponible() &&
                            !tipoAlimentoRepository.findAlimentosEmpaquetados().contains(detalle.getTipoAlimento())){

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
        }
    }

    public Optional<DetalleAportacion> buscarLinea(Long numLinea, List<DetalleAportacion> detalles ){
        return detalles
                .stream()
                .filter(d -> d.getDetalleAportacionPK().getLineaId() == numLinea)
                .findFirst();
    }

}
