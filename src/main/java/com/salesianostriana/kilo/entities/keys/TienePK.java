package com.salesianostriana.kilo.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor @NoArgsConstructor
@Data
public class TienePK implements Serializable {

    private Long cajaId;

    private Long tipoAlimentoId;

}
