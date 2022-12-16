package com.salesianostriana.kilo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CajaResponseDTO {

    @JsonView({View.CajaView.GenericResponseView.class})
    private Long id;

    @JsonView({View.CajaView.GenericResponseView.class})
    private String qr;

    @JsonView({View.CajaView.GenericResponseView.class})
    private int numCaja;

    @JsonView({View.CajaView.GenericResponseView.class})
    private double kilosTotales;

    private String nombreDestinatario;

    @JsonView({View.CajaView.DetailResponseView.class})
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
