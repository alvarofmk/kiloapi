package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.cajas.CajaResponseDTO;
import com.salesianostriana.kilo.dtos.cajas.CreateCajaDTO;
import com.salesianostriana.kilo.dtos.cajas.EditCajaDTO;
import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.TienePK;
import com.salesianostriana.kilo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    private final TieneRepository tieneRepository;

    private final KilosDisponiblesService kilosDisponiblesService;

    private final DestinatarioService destinatarioService;

    private final TipoAlimentoService tipoAlimentoService;
    private final TipoAlimentoRepository tipoAlimentoRepository;
    private final DestinatarioRepository destinatarioRepository;
    private final KilosDisponiblesRepository kilosDisponiblesRepository;

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
        Optional<Destinatario> newDest = editCajaDTO.getDestinatarioId() == null ? Optional.empty() : destinatarioRepository.findById(editCajaDTO.getDestinatarioId());
        if(editCajaDTO.getNumero() <= 0 || newDest.isEmpty())
            return Optional.empty();
        return repository.findById(id).map( cajaToEdit -> {
            Destinatario actual = cajaToEdit.getDestinatario();
            cajaToEdit.setQr(editCajaDTO.getQr());
            cajaToEdit.setNumCaja(editCajaDTO.getNumero());
            if(actual != null)
                cajaToEdit.removeDestinatario(actual);
            cajaToEdit.addDestinatario(newDest.get());
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
                Double kilosDispo = alim.getTipoAlimento().getKilosDisponibles().getCantidadDisponible();
                kilosDispo += alim.getCantidadKgs();
                kilosDisponiblesService.add(new KilosDisponibles(alim.getTipoAlimento().getId(), kilosDispo));
            });
            if(toDelete.getDestinatario() != null){
                toDelete.removeDestinatario(toDelete.getDestinatario());
            }
            repository.delete(toDelete);
        }
    }

    public Optional<CajaResponseDTO> deleteAlimFromCaja (Long idCaja, Long idAlim) {
        Optional<Caja> c = repository.findById(idCaja);
        Optional<TipoAlimento> alim = tipoAlimentoRepository.findById(idAlim);
        Optional<KilosDisponibles> kilosDisponibles = kilosDisponiblesService.findById(idAlim);

        if (c.isPresent() && alim.isPresent()) {
            Caja caja = c.get();

            Tiene t = Tiene.builder()
                    .caja(caja)
                    .tipoAlimento(alim.get())
                    .tienePK(new TienePK(caja.getId(), alim.get().getId()))
                    .build();


            if (caja.getAlimentos().contains(t)) {

                caja.getAlimentos().stream().forEach( al -> {
                    if (al.getTienePK().getTipoAlimentoId() == idAlim) {
                        Double kilosEnCaja = alim.get().getKilosDisponibles().getCantidadDisponible();
                        Double cant = al.getCantidadKgs() + kilosEnCaja;
                        kilosDisponiblesService.add(new KilosDisponibles(idAlim, cant));
                    }
                });

                caja.removeTiene(t);

                repository.save(caja);

                return Optional.of(CajaResponseDTO.of(caja));

            }
        }
        return Optional.empty();

    }
}
