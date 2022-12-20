package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.dtos.DestinatarioResponseDTO;
import com.salesianostriana.kilo.dtos.destinatarios.CreateDestinatarioDTO;
import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.repositories.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinatarioService {

    private final DestinatarioRepository destinatarioRepository;

    public List<DestinatarioResponseDTO> getAllDestinatarios(){

        List<DestinatarioResponseDTO> destinatarios = destinatarioRepository.getDestinatariosConKilos();
        destinatarios.forEach(d ->
                d.setNumerosDeCajas(destinatarioRepository.numerosdeCaja(d.getId()))
            );
        return destinatarios;
    }

    public void deleteById(Long id) {
        Optional<Destinatario> opt = destinatarioRepository.findById(id);
        Destinatario toDelete = null;
        if (opt.isPresent())
            toDelete = opt.get();
            toDelete.getCajas().forEach(caja -> caja.setDestinatario(null));
            destinatarioRepository.delete(toDelete);
    }

    public Optional<DestinatarioResponseDTO> getSummary(Long id) {
        return destinatarioRepository.findSummary(id);
    }

    public Optional<Destinatario> findById(Long id){ return destinatarioRepository.findById(id); }

    public Destinatario createDestinatario(CreateDestinatarioDTO createDestinatarioDTO){
        return destinatarioRepository.save(Destinatario.builder()
                .nombre(createDestinatarioDTO.getNombre())
                .direccion(createDestinatarioDTO.getDireccion())
                .personaContacto(createDestinatarioDTO.getPersonaContacto())
                .telefono(createDestinatarioDTO.getTelefono())
                .build()
        );
    }

    public Optional<Destinatario> editDestinatario(Long id, CreateDestinatarioDTO editDest){
        Optional<Destinatario> destinatario = destinatarioRepository.findById(id);

        if(destinatario.isPresent()){
            if(editDest.getNombre()== null || editDest.getDireccion()==null)
                return Optional.empty();
            else
                return destinatario.map(d -> {
                    d.setNombre(editDest.getNombre());
                    d.setDireccion(editDest.getDireccion());
                    d.setPersonaContacto(editDest.getPersonaContacto()==null ? d.getPersonaContacto() : editDest.getPersonaContacto());
                    d.setTelefono(editDest.getTelefono()==null ? d.getTelefono() : editDest.getTelefono());
                    return destinatarioRepository.save(d);
                });
        }
        else
            return Optional.empty();
    }

}
