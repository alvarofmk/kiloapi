package com.salesianostriana.kilo.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinatarioResponseDTO {

    private String nombre, direccion, personaContacto, telefono;
    private long numeroCajas;
    private double kilosTotales;

}
