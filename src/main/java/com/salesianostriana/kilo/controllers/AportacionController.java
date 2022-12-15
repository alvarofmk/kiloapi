package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.CajaResponseDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
