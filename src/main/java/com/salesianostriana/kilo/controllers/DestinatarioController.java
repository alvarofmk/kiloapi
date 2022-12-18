package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.DestinatarioResponseDTO;
import com.salesianostriana.kilo.dtos.destinatarios.CreateDestinatarioDTO;
import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.services.DestinatarioService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destinatario/")
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    @Operation(summary = "Borra un destinatario por su id",
            description = "Si el destinatario a borrar ya tiene cajas asociadas, " +
                    "el método setea el destinatario a nulo en todas esas cajas antes de borrar definitivamente.")
    @ApiResponse(responseCode = "204", description = "Destinatario borrado con éxito",
            content = @Content)
    @Parameter(description = "El id del destinatario a borrar", name = "id", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestinatario(@PathVariable Long id){
        destinatarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Muestra un destinatario y un resumen de las cajas asignadas a él")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado al destinatario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Blizzard",
                                        "direccion": "Algún sitio de china",
                                        "personaContacto": "Jeff Kaplan",
                                        "telefono": "666 666 666",
                                        "numeroCajas": 1,
                                        "kilosTotales": 6.0
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra al destinatario",
                    content = @Content)
    })
    @Parameter(description = "El id del destinatario a buscar", name = "id", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<DestinatarioResponseDTO> getDestinatarioSummary(@PathVariable Long id) {
        return ResponseEntity.of(destinatarioService.getSummary(id));
    }

    @Operation(summary = "Muestra un destinatario y el detalle de las cajas asignadas a él")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado al destinatario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioResponseDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "nombre": "Blizzard",
                                        "direccion": "Algún sitio de china",
                                        "personaContacto": "Jeff Kaplan",
                                        "telefono": "666 666 666",
                                        "cajas": [
                                            {
                                                "numCaja": 7,
                                                "kilosTotales": 1.0,
                                                "contenido": [
                                                    {
                                                        "nombre": "Chocolate",
                                                        "kg": 1.0
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra al destinatario",
                    content = @Content)
    })
    @Parameter(description = "El id del destinatario a buscar", name = "id", required = true)
    @JsonView(View.DestinatarioView.DetailedDestinatarioView.class)
    @GetMapping("/{id}/detalle")
    public ResponseEntity<DestinatarioResponseDTO> getDestinatarioDetail(@PathVariable Long id){
        return ResponseEntity.of(destinatarioService.findById(id).map(DestinatarioResponseDTO::of));
    }

    @Operation(summary = "Crea un nuevo destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Destinatario creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": 10,
                                        "nombre": "Hijas de la caridad",
                                        "direccion": "Calle Sin nombre Nº7",
                                        "personaContacto": "Sor María",
                                        "telefono": "689624528",
                                        "cajas": [],                                  
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400", description = "Los datos proporcionados no son correctos",
                    content = @Content) })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos del nuevo destinatario",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateDestinatarioDTO.class),
                    examples = @ExampleObject(value = """
                            {
                                "nombre": "Hijas de la caridad",
                                "direccion": "Calle Sin nombre Nº7",
                                "personaContacto": "Sor María",
                                "telefono": "689624528",                          
                            }
                            """)
            )}
    )
    @PostMapping("/")
    public ResponseEntity<Destinatario> createDestinatario(@RequestBody CreateDestinatarioDTO newDest){
        if(newDest.getNombre()!= null && newDest.getDireccion()!= null)
            return ResponseEntity.status(HttpStatus.CREATED).body(destinatarioService.createDestinatario(newDest));
        else
            return ResponseEntity.badRequest().build();
    }


}
