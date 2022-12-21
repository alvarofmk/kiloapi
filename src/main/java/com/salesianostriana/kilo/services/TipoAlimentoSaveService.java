package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoAlimentoSaveService {

    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimento save(TipoAlimento tipoAlimento){ return tipoAlimentoRepository.save(tipoAlimento); }
}
