package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.repositories.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinatarioService {

    private final DestinatarioRepository destinatarioRepository;

    public void deleteById(Long id) {
        Optional<Destinatario> opt = destinatarioRepository.findById(id);
        Destinatario toDelete = null;
        if (opt.isPresent())
            toDelete = opt.get();
            toDelete.getCajas().forEach(caja -> caja.setDestinatario(null));
            destinatarioRepository.delete(toDelete);
    }
}
