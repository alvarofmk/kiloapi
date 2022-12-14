package com.salesianostriana.kilo.entities;


import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class DetalleAportacion {


    @EmbeddedId
    private DetalleAportacionPK detalleAportacionPK;

    @ManyToOne
    @MapsId("aportacionId")
    @JoinColumn(name = "aportacion_id", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_APORTACION"))
    private Aportacion aportacion;


    private double cantidadKg;


    @ManyToOne
    @JoinColumn(name = "tipo_alimento", foreignKey = @ForeignKey(name = "FK_DETALLEAPORTACION_TIPOALIMENTO"))
    private TipoAlimento tipoAlimento;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleAportacion that = (DetalleAportacion) o;
        return detalleAportacionPK.equals(that.detalleAportacionPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detalleAportacionPK);
    }

    @Override
    public String toString() {
        return "DetalleAportacion{" +
                "detalleAportacionPK=" + detalleAportacionPK +
                ", cantidadKg=" + cantidadKg +
                '}';
    }



}
