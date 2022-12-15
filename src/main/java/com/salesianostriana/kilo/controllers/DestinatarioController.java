package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.services.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
