package com.salesianostriana.kilo.services;



import com.salesianostriana.kilo.dtos.RankQueryResponseDTO;
import com.salesianostriana.kilo.dtos.RankingResponseDTO;

import com.salesianostriana.kilo.dtos.clase.CreateClaseDTO;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ClaseService {

    private final ClaseRepository repository;

    public Clase add(Clase c) {
        return repository.save(c);
    }

    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }


    public Double findKilos(Long id) { return repository.findKilos(id); }




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
        AtomicInteger position = new AtomicInteger(1);
        List<RankQueryResponseDTO> resumenAportaciones = repository.findClasesOrderedByRank();
        List<RankingResponseDTO> result = new ArrayList<>(resumenAportaciones.stream().collect(groupingBy(RankQueryResponseDTO::getClaseId)).values().stream().map(v -> {
            return new RankingResponseDTO(v.get(0).getNombre(), v.size(), v.stream().mapToDouble(RankQueryResponseDTO::getSumaKilos).average().getAsDouble(), v.stream().mapToDouble(RankQueryResponseDTO::getSumaKilos).sum());
        }).toList());
        Collections.sort(result);
        result.stream().forEachOrdered(r -> {
            r.setPosicion(position.get());
            position.getAndIncrement();
        });
        return result;
    }

    /*
    public void deleteClase(Long id) {

        if (repository.findById(id).isPresent()) repository.deleteById(id);

    }
    
     */

    public void deleteClase(Long id) {
        Optional<Clase> c = repository.findById(id);

        if (c.isPresent()) {
            Clase cl = c.get();
            cl.getAportaciones()
                    .forEach( clase -> {
                        clase.setClase(null);
                    });

            repository.delete(cl);
        }
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }



}
