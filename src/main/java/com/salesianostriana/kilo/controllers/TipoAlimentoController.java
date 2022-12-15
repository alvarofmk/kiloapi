package com.salesianostriana.kilo.controllers;


import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.services.TipoAlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tipoAlimento")
@RequiredArgsConstructor
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;

    @GetMapping("/")
    public ResponseEntity<List<TipoAlimento>> getAllTipoAlimento() {
        List<TipoAlimento> lista = tipoAlimentoService.findAll();

        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        }
    }
}
