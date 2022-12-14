package com.salesianostriana.kilo.entities;

import lombok.*;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Caja {

    @Id @GeneratedValue
    private Long id;

    private String qr;

    private int numCaja;

    private double kilosTotales;
  
    @Builder.Default
    @OneToMany(mappedBy = "alimentos")
    private Set<Tiene> alimentos = new HashSet<Tiene>();

    @ManyToOne
    @JoinColumn(name = "destinatario", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    private Destinatario destinatario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caja caja = (Caja) o;
        return id.equals(caja.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Caja{" +
                "id=" + id +
                ", qr='" + qr + '\'' +
                ", numCaja=" + numCaja +
                ", kilosTotales=" + kilosTotales +
                '}';
    }
  
    public void addDestinatario(Destinatario d) {
        this.destinatario = d;
        d.getCajas().add(this);
    }

    public void removeDestinatario(Destinatario d) {
        d.getCajas().remove(this);
        this.destinatario= null;
    }
  
}
