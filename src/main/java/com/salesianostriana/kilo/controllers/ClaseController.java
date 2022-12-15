package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.dtos.ClaseResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.services.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/clase")
public class ClaseController {


    private final ClaseService service;

    @Operation(summary = "Lista una clase segun su Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clase por Id encontrada",
                    content = { @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                            "id" = 123,
                            "nombre" = "2DAM",
                            "tutor" = "Luis Miguel Lopez",
                            "numAportaciones" = 3,
                            "totalKilos" = 12.6
                        }
                    """))
            }),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ninguna clase con el id Id indicado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClaseResponseDTO> findById (
            @Parameter(description = "ID de la clase buscada", required = true)
            @PathVariable Long id
    ) {
        Optional<Clase> c = service.findById(id);

        if (c == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.of(c.map(ClaseResponseDTO::of));
        }
    }

    @Operation(summary = "Lista todas las clases")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de clases encontrado",
                    content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Clase.class)),
                            examples = @ExampleObject(value = """
                                    [
                                         {
                                            "id": 1,
                                            "nombre": "2DAM",
                                            "tutor": "Luis Miguel Lopez",
                                            "numAportaciones" = 3,
                                            "totalKilos" = 12.6
                                        },
                                        {
                                            "id": 2,
                                            "nombre": "1DAM",
                                            "tutor": "Miguel Campos",
                                            "numAportaciones" = 5,
                                            "totalKilos" = 0.0
                                        }
                                    ]
                                    """
                            ))
            }),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay clases",
                    content = @Content
            )
    })
    @GetMapping("/")
    public ResponseEntity<List<ClaseResponseDTO>> findAll() {
        List<Clase> clases = service.findAll();

        if (clases.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            return ResponseEntity.ok(
                    clases.stream()
                            .map(ClaseResponseDTO::of)
                            .toList()
            );
        }
    }
}
