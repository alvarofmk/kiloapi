package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.requests.CreateCajaDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.Tiene;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.entities.keys.TienePK;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.TieneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    private final TieneRepository tieneRepository;

    private final KilosDisponiblesService kilosDisponiblesService;

    private final TipoAlimentoService tipoAlimentoService;

    public List<Caja> findAll(){
        return repository.findAll();
    }

    public Caja createCaja(CreateCajaDTO createCajaDTO) {
        String url = "http://localhost:8080/caja/";

        Caja newCaja = repository.save(Caja.builder()
                .numCaja(createCajaDTO.getNumCaja())
                .kilosTotales(0)
                .build());

        newCaja.setQr("http://localhost:8080/caja/" + newCaja.getId());

        return repository.save(newCaja);
    }

    public Optional<Caja> findById(Long id){
        return repository.findById(id);
    }

    public Optional<Caja> addAlimento(Long cajaId, Long tipoAlimentoId, Double cantidad) {
        Optional<Caja> caja = this.findById(cajaId);
        Optional<TipoAlimento> alimento = tipoAlimentoService.findById(tipoAlimentoId);
        Optional<KilosDisponibles> kilos = kilosDisponiblesService.findById(tipoAlimentoId);
        if(caja.isPresent() && alimento.isPresent() && kilos.isPresent()){
            if(kilos.get().getCantidadDisponible() >= cantidad){
                double remaining = kilos.get().getCantidadDisponible() - cantidad;
                Optional<Tiene> tiene = tieneRepository.findById(new TienePK(cajaId, tipoAlimentoId));
                Caja cajaToAdd = caja.get();
                TipoAlimento tipoAlimento = alimento.get();
                if (tiene.isPresent()){
                    cantidad += tiene.get().getCantidadKgs();
                    cajaToAdd.removeTiene(tiene.get());
                }
                cajaToAdd.addTiene(new Tiene(cajaToAdd, tipoAlimento, cantidad));
                repository.save(cajaToAdd);
                kilosDisponiblesService.add(new KilosDisponibles(tipoAlimentoId, remaining));
                return Optional.of(cajaToAdd);
            }
        }
        return Optional.empty();
    }
}
