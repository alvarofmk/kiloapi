package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AportacionService {

    private final AportacionRepository aportacionRepository;

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
        /*List<String> claves = lista
                                .stream()
                                .map(a -> a.getNombreAlimento())
                                .toList();
        List<Double> valores = lista.stream()
                                .map(a -> a.getKgDetalleAportacion())
                                .toList();
        lista.forEach(a -> a.maping(claves));*/
        lista.forEach(a-> a.maping(detalles));
        return lista;

    }

}
