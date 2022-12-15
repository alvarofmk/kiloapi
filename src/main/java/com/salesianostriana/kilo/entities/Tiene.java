package com.salesianostriana.kilo.entities;

import com.salesianostriana.kilo.entities.keys.TienePK;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Tiene {

    public Tiene(Caja caja, TipoAlimento tipoAlimento, double cantidadKgs) {
        this.caja = caja;
        this.tipoAlimento = tipoAlimento;
        this.cantidadKgs = cantidadKgs;

        //asignar ids
        tienePK.setCajaId(this.caja.getId());
        tienePK.setTipoAlimentoId(this.tipoAlimento.getId());

    }

    @EmbeddedId
    private TienePK tienePK = new TienePK();

    @ManyToOne
    @MapsId("cajaId")
    @JoinColumn(name = "caja_id", foreignKey = @ForeignKey(name = "FK_TIENE_CAJA"))
    private Caja caja;

    @ManyToOne
    @MapsId("tipoAlimentoId")
    @JoinColumn(name = "tipo_alimento_id", foreignKey = @ForeignKey(name = "FK_TIENE_TIPO_ALIMENTO"))
    private TipoAlimento tipoAlimento;

    private double cantidadKgs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tiene tiene = (Tiene) o;
        return tienePK.equals(tiene.tienePK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tienePK);
    }

    @Override
    public String toString() {
        return "Tiene{" +
                "tienePK=" + tienePK +
                ", cantidadKgs=" + cantidadKgs +
                '}';
    }
}
