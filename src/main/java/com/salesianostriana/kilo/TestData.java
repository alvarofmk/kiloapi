package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.Clase;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.services.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {

    @Autowired
    private CajaRepository repository;

    @Autowired
    ClaseService claseService;

    @PostConstruct
    public void initData(){
        Caja c1 = Caja.builder()
                .qr("sdfsdfsdf")
                .numCaja(7)
                .build();

        repository.save(c1);


        Clase cl1 = Clase.builder()
                .nombre("2DAM")
                .tutor("Luis Miguel Lopez")
                .build();
        Clase cl2 = Clase.builder()
                .nombre("1DAM")
                .tutor("Miguel Campos")
                .build();

        List<Clase> clases = List.of(cl1, cl2);
        clases.forEach(claseService::add);
        clases.forEach(System.out::println);


    }
}
