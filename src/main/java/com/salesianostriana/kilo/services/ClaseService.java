package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.clase.ClaseResponseDTO;
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

    public Optional<ClaseResponseDTO> findFull (Long id) { return repository.findFull(id) ; }

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

}
