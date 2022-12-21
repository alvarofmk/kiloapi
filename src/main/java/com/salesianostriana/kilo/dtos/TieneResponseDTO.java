package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.Tiene;
import com.salesianostriana.kilo.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TieneResponseDTO {

    @JsonView({View.CajaView.DetailResponseView.class})
    private Long id;

    @JsonView({View.CajaView.DetailResponseView.class, View.DestinatarioView.DetailedDestinatarioView.class})
    private String nombre;

    @JsonView({View.CajaView.DetailResponseView.class, View.DestinatarioView.DetailedDestinatarioView.class})
    private double kg;

    public static TieneResponseDTO of(Tiene tiene){
        return TieneResponseDTO.builder()
                .id(tiene.getTipoAlimento().getId())
                .nombre(tiene.getTipoAlimento().getNombre())
                .kg(tiene.getCantidadKgs())
                .build();
    }

}
