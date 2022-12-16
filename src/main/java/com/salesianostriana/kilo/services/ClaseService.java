package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.clase.CreateClaseDTO;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Clase> editClase(Long id, CreateClaseDTO createClaseDTO) {
        return repository.findById(id).map(c -> {
            c.setNombre(createClaseDTO.getNombre());
            c.setTutor(createClaseDTO.getNombre());

            return repository.save(c);
        });
    }
}
