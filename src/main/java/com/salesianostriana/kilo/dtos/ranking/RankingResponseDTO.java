package com.salesianostriana.kilo.dtos.ranking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RankingResponseDTO implements Comparable<RankingResponseDTO> {

    private int posicion;
    private String nombre;
    private long cantidadAportaciones;
    private double mediaKilos;
    private double totalKilos;

    public RankingResponseDTO(String nombre, long cantidadAportaciones, double mediaKilos, double totalKilos) {
        this.nombre = nombre;
        this.cantidadAportaciones = cantidadAportaciones;
        this.mediaKilos = mediaKilos;
        this.totalKilos = totalKilos;
    }

    @Override
    public int compareTo(RankingResponseDTO o) {
        return this.totalKilos < o.totalKilos ? 1 : -1;
    }
}
