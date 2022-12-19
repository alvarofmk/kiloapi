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

    //Puede que uses id tu tambien durbán :)
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

    @JsonView(View.AportacionView.AportacionByClase.class)
    private Map<String, Double> pares = new HashMap<>();

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

    public AportacionesReponseDTO(LocalDate fecha, String alimento, double kg) {
        this.fecha = fecha;
        nombreAlimento = alimento;
        kgDetalleAportacion = kg;
        pares = null;

    }

    public void maping(String nombre, double kilo) {
        Map<String, Double> mapa = new HashMap<>();
        mapa.put(nombre, kilo);
        this.pares = mapa;
    }


}
