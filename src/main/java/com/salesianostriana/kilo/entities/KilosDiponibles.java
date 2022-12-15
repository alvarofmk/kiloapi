package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class KilosDiponibles {

    @ManyToOne
    @JoinColumn(name = "tipoAlimento", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    @MapsId
    private TipoAlimento tipoAlimento;

    @Id
    private Long id;
    private Double cantidadDisponible;


    public void addTipoAlimento (TipoAlimento t) {
        this.tipoAlimento = t;
        t.getKilosDiponibles().add(this);
    }

    public void removeTipoAlimento(TipoAlimento t){
        t.getKilosDiponibles().remove(this);
        this.tipoAlimento = null;
    }








    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KilosDiponibles that = (KilosDiponibles) o;
        return Objects.equals(tipoAlimento, that.tipoAlimento) && Objects.equals(cantidadDisponible, that.cantidadDisponible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoAlimento, cantidadDisponible);
    }

    @Override
    public String toString() {
        return "KilosDiponibles{" +
                "tipoAlimento=" + tipoAlimento +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
}
