package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.cajas.CreateCajaDTO;
import com.salesianostriana.kilo.dtos.cajas.EditCajaDTO;
import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.TienePK;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.DestinatarioRepository;
import com.salesianostriana.kilo.repositories.TieneRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    private final TieneRepository tieneRepository;

    private final KilosDisponiblesService kilosDisponiblesService;

    private final TipoAlimentoService tipoAlimentoService;
    private final TipoAlimentoRepository tipoAlimentoRepository;
    private final DestinatarioRepository destinatarioRepository;

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

    public Optional<Caja> editCaja(EditCajaDTO editCajaDTO, Long id){
        if(editCajaDTO.getNumero() <= 0)
            return Optional.empty();
        return repository.findById(id).map( cajaToEdit -> {
            cajaToEdit.setQr(editCajaDTO.getQr());
            cajaToEdit.setNumCaja(editCajaDTO.getNumero());
            return repository.save(cajaToEdit);
        });
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

    public void deleteById(Long id) {
        Optional<Caja> c = repository.findById(id);

        if (c.isPresent()) {
            Caja toDelete = c.get();
            toDelete.getAlimentos().forEach(alim -> {
                toDelete.setKilosTotales(toDelete.getKilosTotales() - alim.getCantidadKgs());

                Double kilosDispo = alim.getTipoAlimento().getKilosDisponibles().getCantidadDisponible();

                kilosDispo += alim.getCantidadKgs();

                alim.setCantidadKgs(kilosDispo);
            });

            Destinatario dest = destinatarioRepository.findById(toDelete.getDestinatario().getId()).get();

            dest.getCajas().remove(toDelete);

            repository.delete(toDelete);

        }
    }
}
