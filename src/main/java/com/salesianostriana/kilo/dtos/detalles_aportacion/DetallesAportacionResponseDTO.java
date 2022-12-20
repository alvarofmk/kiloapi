package com.salesianostriana.kilo.dtos.detalles_aportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.views.View;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesAportacionResponseDTO {

    @JsonView({View.KilosDisponiblesView.KilosDisponiblesDetailsView.class})
    private Long aportacionId;

    @JsonView({View.AportacionView.AportacionDetallesView.class, View.KilosDisponiblesView.KilosDisponiblesDetailsView.class})
    private Long numLinea;

    @JsonView({View.AportacionView.AportacionDetallesView.class,
            View.KilosDisponiblesView.KilosDisponiblesDetailsView.class,
            View.AportacionView.AportacionByClase.class})
    private double cantidadKg;

    @JsonView({View.AportacionView.AportacionDetallesView.class,
                View.AportacionView.AportacionByClase.class})
    private String nombre;


    public DetallesAportacionResponseDTO(Long aportacionId, Long numLinea, double cantidadKg){
        this.aportacionId = aportacionId;
        this.numLinea = numLinea;
        this.cantidadKg = cantidadKg;
    }


    public static DetallesAportacionResponseDTO of (DetalleAportacion d){
        return DetallesAportacionResponseDTO.builder()
                .numLinea(d.getDetalleAportacionPK().getLineaId())
                .cantidadKg(d.getCantidadKg())
                .nombre(d.getTipoAlimento().getNombre() != null ? d.getTipoAlimento().getNombre() : null)
                .build();
    }

    public DetallesAportacionResponseDTO(Long id, String nombre, double kg) {
        aportacionId = id;
        this.nombre = nombre;
        cantidadKg = kg;
    }


}
