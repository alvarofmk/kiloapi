package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankingResponseDTO {

    private String nombre;
    private long cantidadAportaciones;
    private double mediaKilos;
    private double totalKilos;

}
