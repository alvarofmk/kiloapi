package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.services.AportacionService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/aportacion")
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionService aportacionService;

    @GetMapping("/{id}")
    public ResponseEntity<Aportacion> getDetallesAportacion(
            @Parameter(description = "ID de la aportación buscada", required = true)
            @PathVariable Long id
    )
    {
        Optional<Aportacion> aportacion = aportacionService.findById(id);
        if(aportacion.isEmpty())
            return ResponseEntity.of(aportacion);

        return ResponseEntity.notFound().build();
    }
}
