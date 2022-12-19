package com.salesianostriana.kilo.dtos;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
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
