package com.salesianostriana.kilo.dtos.ranking;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankQueryResponseDTO {

    private Long claseId;
    private String nombre;
    private double sumaKilos;

}
