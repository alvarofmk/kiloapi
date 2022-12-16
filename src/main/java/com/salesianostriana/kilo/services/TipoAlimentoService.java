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

    private final TipoAlimentoRepository tipoAlimentoRepository;

    public List<TipoAlimento> findAll() {
        return tipoAlimentoRepository.findAll();
    }

    public Optional<TipoAlimento> findById(Long id){
        return tipoAlimentoRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return tipoAlimentoRepository.existsById(id);
    }


}
