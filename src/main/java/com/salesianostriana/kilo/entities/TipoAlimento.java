package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
