package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.CajaResponseDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.views.View;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/caja")
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;

    @GetMapping("/")
    @JsonView(View.CajaView.GenericResponseView.class)
    public ResponseEntity<List<CajaResponseDTO>> getAllCajas(){
        List<Caja> result = cajaService.findAll();
        if (!result.isEmpty())
            return ResponseEntity.ok(
                    result.stream()
                    .map(CajaResponseDTO::of)
                    .toList());
        else
            return ResponseEntity.notFound().build();
    }

}
