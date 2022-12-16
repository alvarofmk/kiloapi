package com.salesianostriana.kilo.dtos.cajas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClassDTO {

    private String nombre, tutor;
}