package com.salesianostriana.kilo.dtos.tipo_alimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.views.View;
import lombok.*;


@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoAlimentoDTO {

    @JsonView({View.TipoAlimentoView.AllTipoAlimentoView.class,
    View.TipoAlimentoView.TipoAlimentoByIdView.class})
    private Long id;

    @JsonView({View.TipoAlimentoView.AllTipoAlimentoView.class,
    View.TipoAlimentoView.TipoAlimentoByIdView.class,
            View.TipoAlimentoView.TipoAlimentoRequest.class})
    private String nombre;

    @JsonView({View.TipoAlimentoView.TipoAlimentoByIdView.class})
    private double kilosDisponibles;


    public static TipoAlimentoDTO of(TipoAlimento t) {
        return TipoAlimentoDTO
                .builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .kilosDisponibles(t.getKilosDisponibles().getCantidadDisponible())
                .build();
    }
}
