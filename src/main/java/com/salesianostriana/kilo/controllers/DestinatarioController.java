package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.dtos.CajaResponseDTO;
import com.salesianostriana.kilo.dtos.DestinatarioResponseDTO;
import com.salesianostriana.kilo.services.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

}
