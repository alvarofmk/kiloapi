package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.CajaRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import com.salesianostriana.kilo.services.CajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class TestData {

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

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



    }
}
