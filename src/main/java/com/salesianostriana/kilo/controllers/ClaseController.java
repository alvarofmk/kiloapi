package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.services.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/clase")
public class ClaseController {

    @Autowired
    private ClaseService service;

    @GetMapping("/id")
    public ResponseEntity<Clase> findById (@PathVariable Long id) {
        Optional<Clase> c = service.findById(id);

        if (c == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.of(c);
        }
    }
}
