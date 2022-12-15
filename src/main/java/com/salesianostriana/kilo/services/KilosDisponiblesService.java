package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.repositories.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {

    private final KilosDisponiblesRepository repository;

    public Optional<KilosDisponibles> findById(Long id){
        return repository.findById(id);
    }

    public KilosDisponibles add(KilosDisponibles kilosDisponibles){
        return repository.save(kilosDisponibles);
    }

}
