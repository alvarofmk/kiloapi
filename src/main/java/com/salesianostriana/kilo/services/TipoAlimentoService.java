package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoAlimentoService {

    private final TipoAlimentoRepository repository;

    public Optional<TipoAlimento> findById(Long id){
        return repository.findById(id);
    }

    public List<TipoAlimento> findAll(){
        return repository.findAll();
    }

}
