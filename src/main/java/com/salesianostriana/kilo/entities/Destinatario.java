package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Destinatario {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private String direccion;

    private String personaContacto;

    private String telefono;

    @OneToMany(mappedBy = "destinatario")
    @Builder.Default
    private List<Caja> cajas = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destinatario that = (Destinatario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Destinatario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", personaContacto='" + personaContacto + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
