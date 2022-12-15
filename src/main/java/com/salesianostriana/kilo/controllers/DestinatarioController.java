package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.services.DestinatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destinatario/")
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestinatario(@PathVariable Long id){
        destinatarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
