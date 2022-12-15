package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.KilosDisponiblesRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import com.salesianostriana.kilo.repositories.ClaseRepository;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.services.ClaseService;
import com.salesianostriana.kilo.services.KilosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestData {

    private final CajaRepository cajaRepository;
    private final TipoAlimentoRepository tipoAlimentoRepository;
    private final ClaseService claseService;
    private final KilosDisponiblesRepository kilosDisponiblesRepository;

    @PostConstruct
    public void initData(){
        Caja c1 = Caja.builder()
                .qr("sdfsdfsdf")
                .numCaja(7)
                .build();

        cajaRepository.save(c1);

        TipoAlimento t1 = TipoAlimento.builder()
                .nombre("Pasta")
                .build();

        TipoAlimento t2 = TipoAlimento.builder()
                .nombre("Chocolate")
                .build();

        tipoAlimentoRepository.saveAll(List.of(t1, t2));

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

        KilosDisponibles k1 = KilosDisponibles.builder()
                .cantidadDisponible(10.0)
                .build();

        k1.addTipoAlimento(t1);

        KilosDisponibles k2 = KilosDisponibles.builder()
                .cantidadDisponible(2.0)
                .build();

        k2.addTipoAlimento(t2);

        kilosDisponiblesRepository.saveAll(List.of(k1, k2));



    }
}
