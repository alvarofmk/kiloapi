package com.salesianostriana.kilo.entities.keys;

import com.salesianostriana.kilo.entities.Aportacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor @NoArgsConstructor
@Data
public class DetalleAportacionPK implements Serializable {

    private Long lineaId;

    private Long aportacionId;
}
