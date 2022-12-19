package com.salesianostriana.kilo.dtos.aportaciones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AportacionesReponseDTO {

    //Puede que uses id tu tambien durbán :)
    @JsonView({View.AportacionView.AportacionDetallesView.class})
    private Long id;

    @JsonView({View.AportacionView.AportacionDetallesView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonView({View.AportacionView.AportacionDetallesView.class})
    private List<DetallesAportacionResponseDTO> detallesAportacion = new ArrayList<>();


    private String nombreClase;

    private double cantidadTotalKg;

    public static AportacionesReponseDTO of (Aportacion a){
        return AportacionesReponseDTO.builder()
                .id(a.getId())
                .fecha(a.getFecha())
                .detallesAportacion(a.getDetalleAportaciones()
                        .stream()
                        .map(DetallesAportacionResponseDTO::of)
                        .toList()
                )
                .build();
    }

    public AportacionesReponseDTO(Long id, LocalDate fecha, String clase, double totalKg) {
        this.id = id;
        this.fecha = fecha;
        nombreClase = clase;
        cantidadTotalKg = totalKg;
    }


}
