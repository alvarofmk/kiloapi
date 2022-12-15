package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.repositories.*;
import com.salesianostriana.kilo.services.CajaService;
import com.salesianostriana.kilo.services.ClaseService;
import com.salesianostriana.kilo.services.KilosDisponiblesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestData {

    private final CajaRepository cajaRepository;
    private final TipoAlimentoRepository tipoAlimentoRepository;
    private final ClaseService claseService;
    private final KilosDisponiblesRepository kilosDisponiblesRepository;
    private final AportacionRepository aportacionRepository;
    private final DetalleAportacionRepository detalleAportacionRepository;
    private final DestinatarioRepository destinatarioRepository;


    @PostConstruct
    public void initData(){

        Destinatario d1 = Destinatario.builder()
                .nombre("Mi abuela")
                .telefono("686 567 397")
                .direccion("La giralda basicamente")
                .personaContacto("Pues mi abuela")
                .build();

        Destinatario d2 = Destinatario.builder()
                .nombre("Blizzard")
                .telefono("666 666 666")
                .direccion("Algún sitio de china")
                .personaContacto("Jeff Kaplan")
                .build();

        Caja c1 = Caja.builder()
                .qr("http://localhots:8080/caja/3")
                .numCaja(7)
                .build();

        c1.addDestinatario(d2);

        destinatarioRepository.saveAll(List.of(d1, d2));
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

        Aportacion a1 = Aportacion.builder()
                .fecha(LocalDate.of(2018, 1, 1))
                .build();

        aportacionRepository.save(a1);

        DetalleAportacion d1 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(1L, a1.getId()))
                .cantidadKg(45.8)
                .build();

        a1.addDetalleAportacion(d1);
        d1.addToTipoAlimento(t1);

        detalleAportacionRepository.save(d1);




    }
}
