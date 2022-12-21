package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.kilos_disponibles.KilosDisponiblesDTO;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.repositories.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {

    private final KilosDisponiblesRepository repository;

    private final TipoAlimentoService tipoAlimentoService;

    public Optional<KilosDisponibles> findById(Long id){
        return repository.findById(id);
    }

    public KilosDisponibles add(KilosDisponibles kilosDisponibles){
        return repository.save(kilosDisponibles);
    }

    public List<KilosDisponiblesDTO> findAllKgDisponibles() {
        return repository.getAllKgDisponibles();
    }

    public Optional<KilosDisponiblesDTO> getKgAlimento(Long id){
        KilosDisponiblesDTO detalles;

        if(tipoAlimentoService.findById(id).isPresent()){
            detalles = repository.getAllKgDisponiblesById(id);
            detalles.setListaDetallesConKg(repository.getKgPorAlimentos(id));
            return Optional.of(detalles);
        }
        else
            return Optional.empty();
    }

}
