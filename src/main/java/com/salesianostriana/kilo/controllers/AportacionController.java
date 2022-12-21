package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionRequestDTO;
import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.services.AportacionService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.web.bind.annotation.*;

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
    @Parameter(description = "ID de la aportación buscada", required = true)
    @GetMapping("/{id}")
    @JsonView(View.AportacionView.AportacionDetallesView.class)
    public ResponseEntity<AportacionesReponseDTO> getDetallesAportacion(@PathVariable Long id) {

        return ResponseEntity.of(aportacionService.findById(id).map(AportacionesReponseDTO::of));
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
    @Operation(summary = "Obtiene todas las aportaciones que ha hecho una clase")
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
                                                    "fecha": "2019-01-01",
                                                    "alimentos": {
                                                        "Chocolate": 20.2,
                                                        "Polvorones": 2.3
                                                    }
                                                },
                                                {
                                                    "fecha": "2020-01-01",
                                                    "alimentos": {
                                                        "Chocolate": 5.0,
                                                        "Pasta": 15.7,
                                                        "Polvorones": 10.0
                                                    }
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
    @JsonView(View.AportacionView.AportacionByClaseView.class)
    @GetMapping("/clase/{id}")
    public ResponseEntity<List<AportacionesReponseDTO>> getAllAportacionesByClase(
            @Parameter(
                    description = "ID de la clase la cual buscamos sus aportaciones",
                    schema = @Schema(implementation = Long.class),
                    name = "id",
                    required = true
            )
            @PathVariable Long id) {
        List<AportacionesReponseDTO> lista = aportacionService.findAllAportacionesByClase(id);
        if(lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(lista);
        }
    }

    @Operation(summary = "Borra un detalle de aportación por su número de línea dentro de una aportación especificada por id")
    @ApiResponse(responseCode = "200", description = "Detalle de aportación borrada",
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
                                    """)) })
    @Parameters(value = {
            @Parameter(description = "Id de la aportación", name = "id", required = true),
            @Parameter(description = "Id de la linea de detalle de la aportación", name = "num", required = true)
    })
    @DeleteMapping("/{id}/linea/{num}")
    @JsonView(View.AportacionView.AportacionDetallesView.class)
    public ResponseEntity<AportacionesReponseDTO> deleteDetalleDeAportacion(@PathVariable("id") Long id, @PathVariable("num") Long numLinea){

        aportacionService.deleteLinea(id, numLinea);
        return ResponseEntity.of(aportacionService.findById(id).map(AportacionesReponseDTO::of));
    }

    @Operation(summary = "Borra una aportación por su id")
    @ApiResponse(responseCode = "204", description = "Aportación borrada con éxito",
            content = @Content)
    @Parameter(description = "El id de la clase a borrar", name = "id", required = true)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAportacionById(@PathVariable Long id){

        aportacionService.deleteAportacionById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Crea una nueva aportación")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
    description = "Cuerpo de la petición",
    content = {
            @Content(mediaType = "application/json",
            schema = @Schema(implementation = AportacionRequestDTO.class),
            examples = {
                    @ExampleObject(
                            value = """
                                    {
                                        "idClase": 7,
                                        "lineas": [
                                            {
                                                "idTipo": 5,
                                                "kg": 12.4
                                            },
                                            {
                                                "idTipo": 4,
                                                "kg": 15
                                            },
                                            {
                                                "idTipo": 6,
                                                "kg": 10.9
                                            }
                                        ]
                                    }
                                    """
                    )
            })
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Se ha creado una aportación",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AportacionesReponseDTO.class),
                    examples = {
                            @ExampleObject(
                                    value = """
                                            {
                                                "id": 13,
                                                "fecha": "2022-12-21",
                                                "clase": "1DAM",
                                                "detallesAportacion": [
                                                    {
                                                        "numLinea": 1,
                                                        "cantidadKg": 12.4,
                                                        "nombre": "Chocolate"
                                                    },
                                                    {
                                                        "numLinea": 2,
                                                        "cantidadKg": 15.0,
                                                        "nombre": "Pasta"
                                                    },
                                                    {
                                                        "numLinea": 3,
                                                        "cantidadKg": 10.9,
                                                        "nombre": "Polvorones"
                                                    }
                                                ]
                                            }
                                            """
                            )
                    })
            }),
            @ApiResponse(responseCode = "400",
            description = "Los datos de la aportación son incorrectos",
            content = @Content)
    })
    @JsonView(View.AportacionView.AportacionDetallesView.class)
    @PostMapping("/")
    public ResponseEntity<AportacionesReponseDTO> createAportacion(@JsonView(View.AportacionView.AportacionRequestView.class) @RequestBody AportacionRequestDTO dto) {
        Optional<AportacionesReponseDTO> resultado = aportacionService.createAportacion(dto);
        if(resultado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultado.get());
        }

    }
}
