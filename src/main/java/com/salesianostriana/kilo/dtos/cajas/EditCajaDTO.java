package com.salesianostriana.kilo.dtos.cajas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditCajaDTO {

    private int numero;
    private String qr;

}
