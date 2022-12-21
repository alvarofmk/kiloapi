package com.salesianostriana.kilo.dtos.aportaciones;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.views.View;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AportacionesReponseDTO {

    @JsonView({View.AportacionView.AportacionDetallesView.class,
    View.AportacionView.AllAportacionView.class,
    View.AportacionView.AportacionRequestView.class})
    private Long id;

    @JsonView({View.AportacionView.AportacionDetallesView.class,
            View.AportacionView.AllAportacionView.class,
            View.AportacionView.AportacionByClaseView.class,
            View.AportacionView.AportacionRequestView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonView({View.AportacionView.AllAportacionView.class,
            View.AportacionView.AportacionDetallesView.class})
    private String clase;

    @JsonView({View.AportacionView.AportacionDetallesView.class})
    private List<DetallesAportacionResponseDTO> detallesAportacion = new ArrayList<>();
    @JsonView({View.AportacionView.AllAportacionView.class})
    private double cantidadTotalKg;

    private String nombreAlimento;

    private double kgDetalleAportacion;

    @JsonView({View.AportacionView.AportacionByClaseView.class,
            View.AportacionView.AportacionRequestView.class})
    @Builder.Default
    private Map<String, Double> alimentos = new HashMap<>();

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private Long idClase;

    @JsonView(View.AportacionView.AportacionRequestView.class)
    private List<LineaDTO> lineas = new ArrayList<>();


    public static AportacionesReponseDTO of (Aportacion a){
        return AportacionesReponseDTO.builder()
                .id(a.getId())
                .fecha(a.getFecha())
                .clase(a.getClase().getNombre())
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
        this.clase = clase;
        cantidadTotalKg = totalKg;
    }

    public AportacionesReponseDTO(Long id, LocalDate fecha) {
        this.id = id;
        this.fecha = fecha;
        alimentos = null;

    }

    public AportacionesReponseDTO (Long idAportacion, Long idClase, List<LineaDTO> pares) {
        id = idAportacion;
        this.idClase = idClase;
        lineas = pares;
    }

    @JsonAnySetter
    public AportacionesReponseDTO setPares(List<DetallesAportacionResponseDTO> detalles, Long id) {

        Map<String, Double> mapa = new HashMap<>();
        detalles.forEach(d -> {
            if(d.getAportacionId() == id && !mapa.containsKey(d.getNombre())) {
                mapa.put(d.getNombre(), d.getCantidadKg());
            }else if(d.getAportacionId() == id && mapa.containsKey(d.getNombre())) {
                mapa.put(d.getNombre(), (mapa.get(d.getNombre()) + d.getCantidadKg()));
            }
        });
        alimentos = mapa;

        return AportacionesReponseDTO
                .builder()
                .id(this.id)
                .fecha(this.fecha)
                .alimentos(mapa)
                .build();
    }


}
