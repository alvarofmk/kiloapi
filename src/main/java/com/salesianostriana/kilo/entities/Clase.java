package com.salesianostriana.kilo.entities;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Clase {

    @Id @GeneratedValue
    private Long id;

    private String nombre, tutor;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    private Set<Aportacion> aportaciones = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clase clase = (Clase) o;
        return Objects.equals(id, clase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
