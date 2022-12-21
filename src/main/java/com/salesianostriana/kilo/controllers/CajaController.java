package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.cajas.CajaResponseDTO;
import com.salesianostriana.kilo.dtos.cajas.CreateCajaDTO;
import com.salesianostriana.kilo.dtos.cajas.EditCajaDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.services.DestinatarioService;
import com.salesianostriana.kilo.services.TipoAlimentoService;
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
@RequestMapping("/caja")
@RequiredArgsConstructor
@Tag(name = "Caja", description = "Este es el controlador para gestionar las cajas")
public class CajaController {

    private final CajaService cajaService;

    @Operation(summary = "Obtiene todas las cajas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cajas encontradas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CajaResponseDTO.class)),
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "id": 1,
                                            "qr": "http://localhost:8080/caja/1",
                                            "numCaja": 7,
                                            "kilosTotales": 12.5
                                        },
                                        {
                                            "id": 2,
                                            "qr": "http://localhost:8080/caja/2",
                                            "numCaja": 14,
                                            "kilosTotales": 7.0
                                        }
                                    ]
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No se encuentra ninguna caja",
                    content = @Content) })
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

    @Operation(summary = "Crea una nueva caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caja creada con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Caja.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 2,
                                        "qr": "http://localhost:8080/caja/2",
                                        "numCaja": 1,
                                        "kilosTotales": 0.0,
                                        "alimentos": [],
                                        "destinatario": null
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400", description = "Hay algún error en los datos",
                    content = @Content) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Los datos para crear la caja")
    @PostMapping("/")
    public ResponseEntity<Caja> createCaja(@RequestBody CreateCajaDTO createCajaDTO){
        if (createCajaDTO.getNumCaja() <= 0)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.createCaja(createCajaDTO));
    }

    @Operation(summary = "Añade kilos de un tipo de alimento a una caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kilos añadidos con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 1,
                                        "qr": "http://localhost:8080/caja/1",
                                        "numCaja": 7,
                                        "kilosTotales": 4.0,
                                        "contenido": [
                                            {
                                                "id": 2,
                                                "nombre": "Pasta",
                                                "kg": 3.0
                                            },
                                            {
                                                "id": 3,
                                                "nombre": "Chocolate",
                                                "kg": 1.0
                                            }
                                        ]
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400", description = "Alguno de los datos está incorrecto",
                    content = @Content)
    })
    @Parameters(value = {
            @Parameter(description = "El id de la caja", name = "id", required = true),
            @Parameter(description = "El id del alimento a añadir", name = "idAlimento", required = true),
            @Parameter(description = "La cantidad de alimento a añadir", name = "cantidad", required = true)
    })
    @JsonView(View.CajaView.DetailResponseView.class)
    @PostMapping("/{id}/tipo/{idAlimento}/kg/{cantidad}")
    public ResponseEntity<CajaResponseDTO> addAlimento(@PathVariable Long id, @PathVariable Long idAlimento, @PathVariable Double cantidad){
        Optional<Caja> result = cajaService.addAlimento(id, idAlimento, cantidad);
        if (result.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(CajaResponseDTO.of(result.get()));
        else
            return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Edita una caja por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caja editada con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 3,
                                        "qr": "No me lo se",
                                        "numCaja": 10,
                                        "kilosTotales": 1.0,
                                        "contenido": [
                                            {
                                                "id": 5,
                                                "nombre": "Chocolate",
                                                "kg": 1.0
                                            }
                                        ]
                                    }
                                    """))}),
            @ApiResponse(responseCode = "400", description = "Los datos son incorrectos",
                    content = @Content)
    })
    @Parameter(description = "El id de la caja a modificar", name = "id", required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Los datos actualizados de la caja")
    @JsonView(View.CajaView.DetailResponseView.class)
    @PutMapping("/{id}")
    public ResponseEntity<CajaResponseDTO> editCaja(@PathVariable Long id, @RequestBody EditCajaDTO editCajaDTO){
        Optional<Caja> result = cajaService.editCaja(editCajaDTO, id);
        return result.isPresent() ? ResponseEntity.ok(result.map(CajaResponseDTO::of).get()) : ResponseEntity.badRequest().build();
    }


    @Operation(summary = "Borra una caja por su id",
            description = "Si la caja a borrar ya tiene destinatario asociado, " +
                    "el método elimina la caja del listado de cajas de su destinatario.")
    @ApiResponse(responseCode = "204", description = "Caja borrada con éxito",
            content = @Content)
    @Parameter(description = "El id de la caja a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCaja(@PathVariable Long id) {
        cajaService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Lista una caja segun su Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Caja por Id encontrada",
                    content = { @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                                    {
                                                        "id": 3,
                                                        "qr": "http://localhots:8080/caja/3",
                                                        "numCaja": 7,
                                                        "kilosTotales": 0.0,
                                                        "nombreDestinatario": "Blizzard",
                                                        "contenido": []
                                                    }
                                    """))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ninguna caja con el id Id indicado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CajaResponseDTO> getById(
            @Parameter(description = "ID de la caja buscada", required = true)
            @PathVariable Long id) {
        return ResponseEntity.of(cajaService.findById(id).map(CajaResponseDTO::of));
    }


    @Operation(summary = "ELimina un alimento de una caja")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Alimento borrado",
                    content = { @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                                    {
                                                        "id": 3,
                                                        "qr": "http://localhost:8080/caja/3",
                                                        "numCaja": 7,
                                                        "kilosTotales": 0.0,
                                                        "contenido": []
                                                    }
                                    """))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ninguna caja con el id Id indicado ",
                    content = @Content
            )
    })
    @JsonView(View.CajaView.DetailResponseView.class)
    @DeleteMapping("/{id}/tipo/{idTipoAlim}")
    public ResponseEntity<CajaResponseDTO> deleteAlimFromCaja (@PathVariable Long id, @PathVariable Long idTipoAlim) {

        return ResponseEntity
                .of(cajaService.deleteAlimFromCaja(id, idTipoAlim));

    }
}
