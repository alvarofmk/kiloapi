package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.dtos.clase.ClaseResponseDTO;
import com.salesianostriana.kilo.dtos.clase.CreateClaseDTO;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@Tag(name = "Clase", description = "Controlador de la entidad clase")
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
        );}
    }

    @Operation(summary = "Crea una nueva clase")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "La clase ha sido creada",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                        {
                                            "id" = 123,
                                            "nombre" = "2DAM",
                                            "tutor" = "Luis Miguel Lopez",
                                            "aportaciones" = []
                                        }
                                    """))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "No se ha podido crear la clase",
                    content = @Content
            )
    })
    @PostMapping("/")
    public ResponseEntity<Clase> createClass(@RequestBody CreateClaseDTO createClaseDTO) {
        if (createClaseDTO.getNombre() == null || createClaseDTO.getTutor() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createClase(createClaseDTO));
        }

    }

    @Operation(summary = "Edita clase")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La clase ha sido ceditada correctamente",
                    content = {@Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                        {
                                            "id" = 123,
                                            "nombre" = "Clase editada",
                                            "tutor" = "Tutor editado",
                                            "aportaciones" = []
                                        }
                                    """))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "No se ha podido editar la clase",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClaseResponseDTO> editClase(
            @Parameter(description = "ID de la clase a editar", required = true)
            @PathVariable Long id,
            @RequestBody CreateClaseDTO claseEdit ) {
        Optional<Clase> oldClase = service.findById(id);

        if (oldClase.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Clase claseData = oldClase.get();

        oldClase.map(c -> {
            c.setNombre(claseEdit.getNombre());
            c.setTutor(claseEdit.getTutor());

            return c;
        });


        return ResponseEntity
                .of(oldClase.map(ClaseResponseDTO::of));
    }

    @Operation(summary = "Borra una clase por su id",
            description = "Si el destinatario a borrar ya tiene aportaciones asociadas, " +
                    "el método setea la clase a nula en todas las aportaciones.")
    @ApiResponse(responseCode = "204", description = "Clase borrada con éxito",
            content = @Content)
    @Parameter(description = "El id de la clase a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClase(@PathVariable Long id) {
        service.deleteClase(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
