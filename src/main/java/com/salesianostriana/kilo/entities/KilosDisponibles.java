package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class KilosDisponibles {

    @ManyToOne
    @JoinColumn(name = "tipoAlimento", foreignKey = @ForeignKey(name = "FK_KILOSDISPONIBLES_TIPOALIMENTO"))
    @MapsId
    private TipoAlimento tipoAlimento;

    @Id
    private Long id;
    private Double cantidadDisponible;

    public KilosDisponibles(Long id, double cantidad) {
        this.id = id;
        this.cantidadDisponible = cantidad;
    }


    public void addTipoAlimento (TipoAlimento t) {
        this.id = t.getId();
        this.tipoAlimento = t;
        t.getKilosDisponibles().add(this);
    }

    public void removeTipoAlimento(TipoAlimento t){
        t.getKilosDisponibles().remove(this);
        this.tipoAlimento = null;
        this.id = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KilosDisponibles that = (KilosDisponibles) o;
        return Objects.equals(tipoAlimento, that.tipoAlimento);
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
