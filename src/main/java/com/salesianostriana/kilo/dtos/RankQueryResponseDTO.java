package com.salesianostriana.kilo.dtos;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankQueryResponseDTO {

    private Long claseId;
    private double sumaKilos;

}
