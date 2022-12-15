package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.requests.CreateCajaDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.Tiene;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.entities.keys.TienePK;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.TieneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    private final TieneRepository tieneRepository;

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

    public Caja addAlimento(Caja caja, TipoAlimento tipoAlimento, Double cantidad) {
        Optional<Tiene> tiene = tieneRepository.findById(new TienePK(caja.getId(), tipoAlimento.getId()));
        if (tiene.isPresent()){
            cantidad += tiene.get().getCantidadKgs();
            caja.removeTiene(tiene.get());
        }
        caja.addTiene(new Tiene(caja, tipoAlimento, cantidad));
        return repository.save(caja);
    }
}
