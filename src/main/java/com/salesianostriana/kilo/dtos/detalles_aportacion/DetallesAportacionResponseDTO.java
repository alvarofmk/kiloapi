package com.salesianostriana.kilo.dtos.detalles_aportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.views.View;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesAportacionResponseDTO {

    @JsonView(View.DetalleAportacionView.class)
    private Long numLinea;

    @JsonView(View.DetalleAportacionView.class)
    private double cantidadKg;

    @JsonView(View.DetalleAportacionView.NombreAlimentoView.class)
    private String nombre;


    public static DetallesAportacionResponseDTO of (DetalleAportacion d){
        return DetallesAportacionResponseDTO.builder()
                .numLinea(d.getDetalleAportacionPK().getLineaId())
                .cantidadKg(d.getCantidadKg())
                .nombre(d.getTipoAlimento().getNombre() != null ? d.getTipoAlimento().getNombre() : null)
                .build();
    }
}
