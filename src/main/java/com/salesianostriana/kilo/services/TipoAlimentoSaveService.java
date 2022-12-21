package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoAlimentoSaveService {

    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimento save(TipoAlimento tipoAlimento){ return tipoAlimentoRepository.save(tipoAlimento); }

    public boolean existsById(Long id) {
        return tipoAlimentoRepository.existsById(id);
    }

    public Optional<TipoAlimento> findById(Long id) {
        return tipoAlimentoRepository.findById(id);
    }
}
