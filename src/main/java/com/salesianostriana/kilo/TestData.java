package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.services.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class TestData {

    @Autowired
    private CajaRepository repository;

    @PostConstruct
    public void initData(){
        Caja c1 = Caja.builder()
                .qr("sdfsdfsdf")
                .numCaja(7)
                .build();

        repository.save(c1);




    }
}
