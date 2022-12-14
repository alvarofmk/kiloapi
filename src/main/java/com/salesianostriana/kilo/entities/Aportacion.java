package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Aportacion {

    @Id @GeneratedValue
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "clase_id", foreignKey = @ForeignKey(
            name = "FK_APORTACION_CLASE"
    ))
    private Clase clase;

    @OneToMany(mappedBy = "aportacion",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<DetalleAportacion> detalleAportaciones = new ArrayList<>();

    public void addToClase(Clase c){
        this.clase = c;
        c.getAportaciones().add(this);
    }

    public void removeFromClase(Clase c){
        this.clase = null;
        c.getAportaciones().remove(this);
    }

    public void addDetalleAportacion(DetalleAportacion d) {
        d.setAportacion(this);
        this.detalleAportaciones.add(d);
    }

    public void removeDetalleAportacion(DetalleAportacion d) {
        d.setAportacion(null);
        this.detalleAportaciones.remove(d);
    }

    @PreRemove
    public void setNullAportacion() {
        this.detalleAportaciones.forEach(d -> d.setAportacion(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aportacion that = (Aportacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
