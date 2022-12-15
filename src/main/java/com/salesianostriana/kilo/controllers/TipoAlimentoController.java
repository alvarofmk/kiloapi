package com.salesianostriana.kilo.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.TipoAlimentoDTO;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.services.TipoAlimentoService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tipoAlimento")
@RequiredArgsConstructor
@Tag(name = "Tipo de alimento", description = "Este es el controlador de los tipos de alimento")
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;

    @Operation(summary = "Obtiene todos los tipos de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado tipos de alimento",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TipoAlimentoDTO.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 2,
                                                    "nombre": "Pasta"
                                                },
                                                {
                                                    "id": 3,
                                                    "nombre": "Chocolate"
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado tipos de alimentos",
            content = @Content)
    })
    @GetMapping("/")
    @JsonView(View.TipoAlimentoView.AllTipoAlimentoView.class)
    public ResponseEntity<List<TipoAlimentoDTO>> getAllTipoAlimento() {
        List<TipoAlimento> lista = tipoAlimentoService.findAll();

        List<TipoAlimentoDTO> resultado = lista.stream()
                                                .map(ta -> TipoAlimentoDTO.of(ta))
                                                .toList();

        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(resultado);
        }
    }
}
