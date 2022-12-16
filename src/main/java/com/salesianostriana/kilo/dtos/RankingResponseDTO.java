package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.kilo.entities.Aportacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankingResponseDTO implements Comparable<RankingResponseDTO> {

    private String nombre;
    private long cantidadAportaciones;
    private double mediaKilos;
    private double totalKilos;


    @Override
    public int compareTo(RankingResponseDTO o) {
        return this.totalKilos > o.totalKilos ? 1 : -1;
    }
}
