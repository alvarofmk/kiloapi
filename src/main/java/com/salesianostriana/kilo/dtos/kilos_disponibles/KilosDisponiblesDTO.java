package com.salesianostriana.kilo.dtos.kilos_disponibles;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.views.View;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KilosDisponiblesDTO {

    @JsonView({View.KilosDisponiblesView.class})
    private Long id;

    @JsonView({View.KilosDisponiblesView.class, View.KilosDisponiblesView.KilosDisponiblesDetailsView.class})
    private String nombre;

    @JsonView({View.KilosDisponiblesView.class, View.KilosDisponiblesView.KilosDisponiblesDetailsView.class})
    private double cantidadKg;

    @JsonView({View.KilosDisponiblesView.KilosDisponiblesDetailsView.class})
    private List<DetallesAportacionResponseDTO> listaDetallesConKg = new ArrayList<>();


    public KilosDisponiblesDTO(Long id, String nombre, double cantidadKg){
        this.id = id;
        this.nombre = nombre;
        this.cantidadKg = cantidadKg;
    }

    public KilosDisponiblesDTO(String nombre, double cantidadKg){
        this.nombre = nombre;
        this.cantidadKg = cantidadKg;
    }
}
