package com.salesianostriana.kilo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Aportacion {

    @Id @GeneratedValue
    private Long id;

    private LocalDate fecha;

}
