package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.views.View;
import lombok.*;


@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
public class TipoAlimentoDTO {

    @JsonView(View.TipoAlimentoView.AllTipoAlimentoView.class)
    private Long id;

    @JsonView(View.TipoAlimentoView.AllTipoAlimentoView.class)
    private String nombre;

    public static TipoAlimentoDTO of(TipoAlimento t) {
        return TipoAlimentoDTO
                .builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .build();
    }
}
