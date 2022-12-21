package com.salesianostriana.kilo.dtos.cajas;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.tiene.TieneResponseDTO;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CajaResponseDTO {

    @JsonView({View.CajaView.GenericResponseView.class})
    private Long id;

    @JsonView({View.CajaView.GenericResponseView.class})
    private String qr;

    @JsonView({View.CajaView.GenericResponseView.class, View.DestinatarioView.DetailedDestinatarioView.class})
    private int numCaja;

    @JsonView({View.CajaView.GenericResponseView.class, View.DestinatarioView.DetailedDestinatarioView.class})
    private double kilosTotales;

    @JsonView({View.CajaView.GenericResponseView.class})
    private String nombreDestinatario;

    @JsonView({View.CajaView.DetailResponseView.class, View.DestinatarioView.DetailedDestinatarioView.class})
    private List<TieneResponseDTO> contenido;

    public static CajaResponseDTO of(Caja caja){
        return CajaResponseDTO.builder()
                .id(caja.getId())
                .qr(caja.getQr())
                .numCaja(caja.getNumCaja())
                .kilosTotales(caja.getKilosTotales())
                .nombreDestinatario(caja.getDestinatario() != null ? caja.getDestinatario().getNombre() : "")
                .contenido(caja.getAlimentos().stream().map(TieneResponseDTO::of).toList())
                .build();
    }


}
