package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.dtos.RankingResponseDTO;
import com.salesianostriana.kilo.services.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final ClaseService claseService;


    @Operation(summary = "Obtiene el ranking de las clases",
            description = "Obtiene un ranking de las clases basado en el total de kilos que han aportado a la operación kilo," +
                    "además de detalles sobre el total de kilos aportados, la media por aportación, y el numero de aportaciones" +
                    "de cada clase.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking devuelto con éxito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RankingResponseDTO.class)),
                            examples = @ExampleObject(value = """
                                    [
                                        {
                                            "posicion": 1,
                                            "nombre": "2DAM",
                                            "cantidadAportaciones": 1,
                                            "mediaKilos": 45.8,
                                            "totalKilos": 45.8
                                        },
                                        {
                                            "posicion": 2,
                                            "nombre": "1DAM",
                                            "cantidadAportaciones": 2,
                                            "mediaKilos": 19.1,
                                            "totalKilos": 38.2
                                        }
                                    ]
                                    """)) }),
            @ApiResponse(responseCode = "404", description = "No existe ninguna clase con aportaciones",
                    content = @Content) })
    @GetMapping("/")
    public ResponseEntity<List<RankingResponseDTO>> getRanking(){
        List<RankingResponseDTO> result = claseService.getRanking();
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result);
    }

}
