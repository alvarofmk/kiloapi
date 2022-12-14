package com.salesianostriana.kilo.dtos.destinatarios;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.cajas.CajaResponseDTO;
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

    public DestinatarioResponseDTO(Long id, String nombre, String direccion, String personaContacto, String telefono, double kilosTotales) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
        this.kilosTotales = kilosTotales;
    }

    public DestinatarioResponseDTO(List<Integer> listaCajas){
        this.numerosDeCajas = listaCajas;
    }

    @JsonView({View.DestinatarioView.AllDestinatarioView.class, View.DestinatarioView.JustDestinatarioView.class})
    private Long id;
    @JsonView({View.DestinatarioView.DetailedDestinatarioView.class, View.DestinatarioView.AllDestinatarioView.class, View.DestinatarioView.JustDestinatarioView.class})
    private String nombre, direccion, personaContacto, telefono;
    private long numeroCajas;

    @JsonView({View.DestinatarioView.AllDestinatarioView.class})
    private double kilosTotales;

    @JsonView({View.DestinatarioView.AllDestinatarioView.class})
    private List<Integer> numerosDeCajas;

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
