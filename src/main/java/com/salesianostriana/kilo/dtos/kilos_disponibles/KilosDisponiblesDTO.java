package com.salesianostriana.kilo.dtos.kilos_disponibles;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KilosDisponiblesDTO {

    private Long id;

    private String nombre;

    private double cantidadKg;

    private Long idAportacion;

    private Long numLinea;

    public KilosDisponiblesDTO(Long id, String nombre, double cantidadKg){
        this.id = id;
        this.nombre = nombre;
        this.cantidadKg = cantidadKg;
    }

    public KilosDisponiblesDTO(Long idAportacion, Long numLinea, double cantidadKg){
        this.idAportacion = idAportacion;
        this.numLinea = numLinea;
        this.cantidadKg = cantidadKg;
    }
}
