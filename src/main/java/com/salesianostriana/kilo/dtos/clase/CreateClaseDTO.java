package com.salesianostriana.kilo.dtos.clase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClaseDTO {

    private String nombre, tutor;
}
