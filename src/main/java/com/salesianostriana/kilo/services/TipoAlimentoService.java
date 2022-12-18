package com.salesianostriana.kilo.services;


import com.salesianostriana.kilo.dtos.tipo_alimento.TipoAlimentoDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
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

    private final AportacionService aportacionService;

    private final CajaService cajaService;

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

    public Optional<TipoAlimento> editTipoAlimento(Long id, TipoAlimentoDTO dto) {
        Optional<TipoAlimento> toEdit = tipoAlimentoRepository.findById(id);
        double cantidadTotalKg = tipoAlimentoRepository.getCantidadTotalKg(id);
        if(dto.getKilosDisponibles() < cantidadTotalKg || dto.getNombre() == null) {
            return Optional.empty();
        }
        return toEdit.map(editado -> {
            editado.getKilosDisponibles().setCantidadDisponible(dto.getKilosDisponibles());
            editado.setNombre(dto.getNombre());
            return tipoAlimentoRepository.save(editado);
        });
    }

    public void deleteTipoAlimento(Long id) {
        Optional<TipoAlimento> borrado = tipoAlimentoRepository.findById(id);
        if((!cajaService.tipoAlimentoInTiene(id) && !aportacionService.tipoAlimentoInAportacion(id)) && borrado.isPresent())
            tipoAlimentoRepository.delete(borrado.get());

    }






}
