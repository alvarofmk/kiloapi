package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.RankQueryResponseDTO;
import com.salesianostriana.kilo.dtos.RankingResponseDTO;
import com.salesianostriana.kilo.dtos.clase.ClaseQueryResponseDTO;
import com.salesianostriana.kilo.dtos.clase.CreateClaseDTO;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository repository;

    public Clase add(Clase c) {
        return repository.save(c);
    }

    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }

    public List<Clase> findAll(){
        return repository.findAll();
    }


    public Clase createClase(CreateClaseDTO createClaseDTO) {
        Clase clase = repository.save(
                Clase.builder()
                        .tutor(createClaseDTO.getTutor())
                        .nombre(createClaseDTO.getNombre())
                        .build()
        );

        return repository.save(clase);
    }

    public List<RankingResponseDTO> getRanking(){
        List<RankingResponseDTO> result = new ArrayList<RankingResponseDTO>();
        List<RankQueryResponseDTO> resumenAportaciones = repository.findClasesOrderedByRank();
        List<ClaseQueryResponseDTO> clases = repository.findClassReferences();
        clases.stream().forEach(c -> {
            List<RankQueryResponseDTO> aportacionesDeClase = resumenAportaciones.stream().filter(a -> a.getClaseId() == c.getId()).toList();
            long cantidadAportaciones = aportacionesDeClase.size();
            double mediaKilos = aportacionesDeClase.stream().mapToDouble(a -> a.getSumaKilos()).average().getAsDouble();
            double sumaKilos = aportacionesDeClase.stream().mapToDouble(a -> a.getSumaKilos()).sum();
            result.add(new RankingResponseDTO(c.getNombre(), cantidadAportaciones, mediaKilos, sumaKilos));
        });
        Collections.sort(result);
        return result;
    }
}
