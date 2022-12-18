package com.salesianostriana.kilo.dtos.clase;

import com.salesianostriana.kilo.entities.Clase;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ClaseResponseDTO {

    private Long id;

    private String nombre, tutor;

    private Integer numAportaciones;

    private Double totalKilos;

    /*
    public ClaseResponseDTO(Long id, String nombre, String tutor, Integer numAportaciones, Double totalKilos) {
        this.id = id;
        this.nombre = nombre;
        this.tutor = tutor;
        this.numAportaciones = numAportaciones;
        this.totalKilos = totalKilos;
    }

     */

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
}
