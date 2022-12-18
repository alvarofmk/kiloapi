package com.salesianostriana.kilo.dtos.destinatarios;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDestinatarioDTO {

    private String nombre, direccion, personaContacto, telefono;
}
