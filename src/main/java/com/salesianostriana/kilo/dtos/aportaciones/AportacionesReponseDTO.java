package com.salesianostriana.kilo.dtos.aportaciones;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.views.View;
import lombok.*;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AportacionesReponseDTO {

    @JsonView({View.AportacionView.AportacionDetallesView.class,
    View.AportacionView.AllAportacionView.class})
    private Long id;

    @JsonView({View.AportacionView.AportacionDetallesView.class,
            View.AportacionView.AllAportacionView.class,
            View.AportacionView.AportacionByClase.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;


    @JsonView({View.AportacionView.AportacionDetallesView.class})
    private List<DetallesAportacionResponseDTO> detallesAportacion = new ArrayList<>();
    @JsonView({View.AportacionView.AllAportacionView.class})
    private String nombreClase;
    @JsonView({View.AportacionView.AllAportacionView.class})
    private double cantidadTotalKg;

    private String nombreAlimento;

    private double kgDetalleAportacion;

    /*@JsonView({View.AportacionView.AportacionByClase.class})
    @Builder.Default
    private List<Pair<String, Double>> pares = new ArrayList<>();*/
    @JsonView({View.AportacionView.AportacionByClase.class})
    @Builder.Default
    private Map<String, Double> alimentos = new HashMap<>();


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

    public AportacionesReponseDTO(Long id, LocalDate fecha) {
        this.id = id;
        this.fecha = fecha;
        alimentos = null;

    }
    @JsonAnySetter
    public AportacionesReponseDTO setPares(List<DetallesAportacionResponseDTO> detalles, Long id) {

        /*List<Pair<String, Double>> aux = new ArrayList<>();

        detalles.forEach(d -> {
            if(d.getAportacionId() == id)
                aux.add(Pair.of(d.getNombre(), d.getCantidadKg()));
        });*/


        /*Map<String, Double> mapa = aux.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        pares = mapa;*/

        /*pares = aux;

        return AportacionesReponseDTO
                .builder()
                .id(this.id)
                .fecha(this.fecha)
                .pares(aux)
                .build();*/

        /*Map<String, Double> mapa = aux.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));*/

        Map<String, Double> mapa = new HashMap<>();
        detalles.forEach(d -> {
            List<Double> aux;
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
