package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.services.AportacionService;
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
@RequestMapping("/aportacion")
@RequiredArgsConstructor
@Tag(name = "Aportacion", description = "Controlador de la entidad aportaciones")
public class AportacionController {

    private final AportacionService aportacionService;

    @Operation(summary = "Obtiene los detalles de una aportación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aportación encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionesReponseDTO.class),
                            examples = @ExampleObject(value = """
                                    [
                                        "id": 1,
                                        "fecha": "2001-01-01",
                                        "detallesAportacion": [
                                            {
                                                "numLinea": 1,
                                                "cantidadKg": 10.5,
                                                "nombre": Queso
                                            },
                                            {
                                                "numLinea": 2,
                                                "cantidadKg": 9.5,
                                                "nombre": Arroz
                                            }      
                                        ]                       
                                    ]
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No se encuentra esta aportación",
                    content = @Content) })
    @GetMapping("/{id}")
    @JsonView(View.AportacionView.AportacionDetallesView.class)
    public ResponseEntity<AportacionesReponseDTO> getDetallesAportacion(
            @Parameter(description = "ID de la aportación buscada", required = true)
            @PathVariable Long id
    )
    {
        Optional<Aportacion> aportacion = aportacionService.findById(id);
        return ResponseEntity.of(Optional.of(AportacionesReponseDTO.of(aportacion.get())));

    }
    @Operation(summary = "Obtiene todas las aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado aportaciones",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AportacionesReponseDTO.class)),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 8,
                                                    "fecha": "2018-01-01",
                                                    "nombreClase": "2DAM",
                                                    "cantidadTotalKg": 45.8
                                                },
                                                {
                                                    "id": 9,
                                                    "fecha": "2019-01-01",
                                                    "nombreClase": "1DAM",
                                                    "cantidadTotalKg": 22.5
                                                },
                                                {
                                                    "id": 10,
                                                    "fecha": "2020-01-01",
                                                    "nombreClase": "1DAM",
                                                    "cantidadTotalKg": 15.7
                                                }
                                            ]
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado aportaciones",
            content = @Content)
    })
    @JsonView(View.AportacionView.AllAportacionView.class)
    @GetMapping("/")
    public ResponseEntity<List<AportacionesReponseDTO>> getAllAportaciones() {
        List<AportacionesReponseDTO> lista = aportacionService.findAllAportaciones();
        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        }
    }
    @JsonView(View.AportacionView.AportacionByClase.class)
    @GetMapping("/clase/{id}")
    public ResponseEntity<List<AportacionesReponseDTO>> getAllAportacionesByClase(@PathVariable Long id) {
        List<AportacionesReponseDTO> lista = aportacionService.findAllAportacionesByClase(id);
        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        }
    }
}
