package com.salesianostriana.kilo.dtos.clase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.views.View;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClaseResponseDTO {

    @JsonView({View.ClaseView.GenericClaseView.class})
    private Long id;

    @JsonView({View.ClaseView.GenericClaseView.class})
    private String nombre, tutor;

    @JsonView({View.ClaseView.GenericClaseView.class})
    private Integer numAportaciones;

    @JsonView({View.ClaseView.GenericClaseView.class, View.ClaseView.DetailClaseView.class })
    private Double totalKilos;

    public static ClaseResponseDTO convertClaseToResponse(Clase c, Double kilosTotales) {
        return ClaseResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .tutor(c.getTutor())
                .numAportaciones(c.getAportaciones().size())
                .totalKilos(kilosTotales)
                .build();
    }



    /*
    public static ClaseResponseDTO of(Clase c) {
        Double[] kg = {0.0};
        c.getAportaciones().stream()
                .forEach(a -> {
                    a.getDetalleAportaciones().stream()
                            .forEach(d -> kg[0] += d.getCantidadKg());
                });

        return ClaseResponseDTO.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .tutor(c.getTutor())
                .numAportaciones(c.getAportaciones().size())
                .totalKilos(kg[0])
                .build();

    }

     */
}
