package com.salesianostriana.kilo.services;


import com.salesianostriana.kilo.dtos.TipoAlimentoDTO;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public TipoAlimento createTipoAlimento(TipoAlimentoDTO dto) {

        TipoAlimento creado = TipoAlimento
                                        .builder()
                                        .nombre(dto.getNombre())
                                        .build();

        creado.setKilosDisponibles(KilosDisponibles
                                            .builder()
                                            .id(creado.getId())
                                            .tipoAlimento(creado)
                                            .cantidadDisponible(0.0)
                                            .build());

        return tipoAlimentoRepository.save(creado);


    }




}
