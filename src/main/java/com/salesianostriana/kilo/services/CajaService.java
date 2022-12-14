package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.repositories.CajaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    public List<Caja> findAll(){
        return repository.findAll();
    }

}
