package com.salesianostriana.kilo.dtos.aportaciones;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AportacionesReponseDTO {

    //Puede que uses id tu tambien durbán :)
    @JsonView({View.AportacionView.DetallesAportacionView.class})
    private Long id;

    @JsonView({View.AportacionView.DetallesAportacionView.class})
    private LocalDate fecha;

    @JsonView({View.AportacionView.DetallesAportacionView.class})
    private List<DetalleAportacion> detallesAportacion = new ArrayList<>();

    public static AportacionesReponseDTO of (Aportacion a){
        return AportacionesReponseDTO.builder()
                .id(a.getId())
                .fecha(a.getFecha())
                .build();
    }


}
