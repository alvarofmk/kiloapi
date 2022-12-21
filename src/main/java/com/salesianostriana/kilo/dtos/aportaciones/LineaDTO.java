package com.salesianostriana.kilo.dtos.aportaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineaDTO implements Serializable {

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private Long idTipo;

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private Double kg;
}
