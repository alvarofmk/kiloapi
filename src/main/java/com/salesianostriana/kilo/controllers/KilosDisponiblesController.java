package com.salesianostriana.kilo.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.kilos_disponibles.KilosDisponiblesDTO;
import com.salesianostriana.kilo.services.KilosDisponiblesService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("kilosDisponibles")
@RequiredArgsConstructor
@Tag(name = "Kilos disponibles", description = "Este es el controlador de los kilos disponibles")
public class KilosDisponiblesController {

    private final KilosDisponiblesService kilosDisponiblesService;

    @Operation(summary = "Obtiene todos los kilos disponibles de los alimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado kilos disponibles",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = KilosDisponiblesDTO.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 4,
                                                    "nombre": "Pasta",
                                                    "cantidadKg": 10.0
                                                },
                                                {
                                                    "id": 5,
                                                    "nombre": "Chocolate",
                                                    "cantidadKg": 2.0
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado kilos disponibles",
            content = @Content)
    })
    @GetMapping("/")
    @JsonView(View.KilosDisponiblesView.class)
    public ResponseEntity<List<KilosDisponiblesDTO>> getAllKgDisponibles() {
        List<KilosDisponiblesDTO> result = kilosDisponiblesService.findAllKgDisponibles();
        return result.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Obtiene los detalles de una aportación que contienen el alimento en concreto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista con los detalles de aportación que contienen dicho alimento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KilosDisponiblesDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Pasta",
                                        "cantidadKg": 10.0,
                                        "listaDetallesConKg": [
                                            {
                                                "idAportacion": 8,
                                                "numLinea": 1,
                                                "cantidadKg": 7.5
                                            },
                                            {
                                                "idAportacion": 9,
                                                "numLinea": 3,
                                                "cantidadKg": 4.5
                                            }
                                        ]
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No se encuentra el alimento en ninguna aportación",
                    content = @Content) })
    @Parameter(description = "El id del tipo de alimento que queremos buscar", name = "id", required = true)
    @GetMapping("/{id}")
    @JsonView(View.KilosDisponiblesView.KilosDisponiblesDetailsView.class)
    public ResponseEntity<KilosDisponiblesDTO> getKgDispobilesAlimento(@PathVariable Long id){

        return ResponseEntity.of(kilosDisponiblesService.getKgAlimento(id));
    }
}
