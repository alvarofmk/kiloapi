package com.salesianostriana.kilo.dtos.aportaciones;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AportacionRequestDTO {

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private Long idClase;

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private List<LineaDTO> lineas;
}
