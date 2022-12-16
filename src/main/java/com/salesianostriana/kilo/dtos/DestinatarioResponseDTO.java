package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.Destinatario;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinatarioResponseDTO {

    public DestinatarioResponseDTO(String nombre, String direccion, String personaContacto, String telefono, long numeroCajas, double kilosTotales) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
        this.numeroCajas = numeroCajas;
        this.kilosTotales = kilosTotales;
    }

    @JsonView({View.DestinatarioView.DetailedDestinatarioView.class})
    private String nombre, direccion, personaContacto, telefono;
    private long numeroCajas;
    private double kilosTotales;

    @JsonView({View.DestinatarioView.DetailedDestinatarioView.class})
    private List<CajaResponseDTO> cajas;

    public static DestinatarioResponseDTO of(Destinatario destinatario){
        return DestinatarioResponseDTO.builder()
                .nombre(destinatario.getNombre())
                .direccion(destinatario.getDireccion())
                .personaContacto(destinatario.getPersonaContacto())
                .telefono(destinatario.getTelefono())
                .cajas(destinatario.getCajas().stream().map(CajaResponseDTO::of).toList())
                .build();
    }

}
