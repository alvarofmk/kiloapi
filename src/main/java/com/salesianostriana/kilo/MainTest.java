package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class MainTest {

    @PostConstruct
    public void run() {
        Aportacion a1 = Aportacion
                .builder()
                .fecha(LocalDate.of(2022, 12, 10))
                .build();
    }
}
