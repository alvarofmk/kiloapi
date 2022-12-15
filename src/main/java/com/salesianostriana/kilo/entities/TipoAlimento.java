package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TipoAlimento {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "tipoAlimento",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.EAGER)
    @Builder.Default
    private List<DetalleAportacion> detalleAportaciones = new ArrayList<>();

    @OneToMany(mappedBy = "tipoAlimento")
    @Builder.Default
    private List<KilosDisponibles> kilosDisponibles = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass() && obj != null){
            TipoAlimento tipoAlimento = (TipoAlimento) obj;
            if(tipoAlimento.getId() == this.getId())
                return true;
            else
                return false;
        }else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TipoAlimento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
