package com.salesianostriana.kilo.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.CajaResponseDTO;
import com.salesianostriana.kilo.dtos.requests.CreateCajaDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.services.TipoAlimentoService;
import com.salesianostriana.kilo.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/caja")
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;

    private final TipoAlimentoService tipoAlimentoService;

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
    @PostMapping("/")
    public ResponseEntity<Caja> createCaja(@RequestBody CreateCajaDTO createCajaDTO){
        if (createCajaDTO.getNumCaja() <= 0)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.createCaja(createCajaDTO));
    }


    @JsonView(View.CajaView.DetailResponseView.class)
    @PostMapping("/{id}/tipo/{idAlimento}/kg/{cantidad}")
    public ResponseEntity<CajaResponseDTO> addAlimento(@PathVariable Long id, @PathVariable Long idAlimento, @PathVariable Double cantidad){
        Optional<Caja> caja = cajaService.findById(id);
        Optional<TipoAlimento> alimento = tipoAlimentoService.findById(idAlimento);
        if (caja.isPresent() && alimento.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(CajaResponseDTO.of(cajaService.addAlimento(caja.get(), alimento.get(), cantidad)));
        else
            return ResponseEntity.badRequest().build();
    }

}
