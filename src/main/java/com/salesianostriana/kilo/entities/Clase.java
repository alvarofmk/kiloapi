package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Clase {

    @Id @GeneratedValue
    private Long id;

    private String nombre, tutor;

    @Builder.Default
    @ToString.Exclude
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

    @PreRemove
    public void deleteClase() {

            this.getAportaciones()
                    .forEach( clase -> {
                        clase.setClase(null);
                    });

    }
}
