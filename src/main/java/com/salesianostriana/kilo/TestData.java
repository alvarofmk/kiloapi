package com.salesianostriana.kilo;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.repositories.*;
import com.salesianostriana.kilo.services.ClaseService;
import lombok.RequiredArgsConstructor;
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
    private final AportacionRepository aportacionRepository;
    private final DestinatarioRepository destinatarioRepository;

    private final TieneRepository tieneRepository;


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
                .qr("http://localhost:8080/caja/3")
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

        TipoAlimento t3 = TipoAlimento.builder()
                .nombre("Polvorones")
                .build();

        t1 = tipoAlimentoRepository.save(t1);
        t2 = tipoAlimentoRepository.save(t2);
        t3 = tipoAlimentoRepository.save(t3);
        //tipoAlimentoRepository.saveAll(List.of(t1, t2));

        //t1 = tipoAlimentoRepository.findById(4L).get();
        //t2 = tipoAlimentoRepository.findById(5L).get();

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
                .cantidadDisponible(61.5)
                .build();

        k1.addTipoAlimento(t1);

        KilosDisponibles k2 = KilosDisponibles.builder()
                .cantidadDisponible(22.5)
                .build();

        k2.addTipoAlimento(t2);

        KilosDisponibles k3 = KilosDisponibles.builder()
                .cantidadDisponible(0.0)
                .build();

        k3.addTipoAlimento(t3);

        tipoAlimentoRepository.saveAll(List.of(t1, t2));

        Aportacion a1 = Aportacion.builder()
                .fecha(LocalDate.of(2018, 1, 1))
                .build();

        Aportacion a2 = Aportacion.builder()
                .fecha(LocalDate.of(2019, 1, 1))
                .build();

        Aportacion a3 = Aportacion.builder()
                .fecha(LocalDate.of(2020, 1, 1))
                .build();

        a1.addToClase(cl1);
        a2.addToClase(cl2);
        a3.addToClase(cl2);

        aportacionRepository.saveAll(List.of(a1, a2, a3));

        DetalleAportacion de1 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(1L, a1.getId()))
                .cantidadKg(45.8)
                .build();

        DetalleAportacion de3 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(1L, a2.getId()))
                .cantidadKg(20.2)
                .build();

        DetalleAportacion de2 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(2L, a2.getId()))
                .cantidadKg(2.3)
                .build();

        DetalleAportacion de4 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(1L, a3.getId()))
                .cantidadKg(15.7)
                .build();

        DetalleAportacion de5 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(2L, a3.getId()))
                .cantidadKg(10)
                .build();

        DetalleAportacion de6 = DetalleAportacion.builder()
                .detalleAportacionPK(new DetalleAportacionPK(3L, a3.getId()))
                .cantidadKg(5)
                .build();


        a1.addDetalleAportacion(de1);
        a2.addDetalleAportacion(de2);
        a2.addDetalleAportacion(de3);
        a3.addDetalleAportacion(de4);
        a3.addDetalleAportacion(de5);
        a3.addDetalleAportacion(de6);
        de1.addToTipoAlimento(t1);
        de2.addToTipoAlimento(t3);
        de3.addToTipoAlimento(t2);
        de4.addToTipoAlimento(t1);
        de5.addToTipoAlimento(t3);
        de6.addToTipoAlimento(t1);

        aportacionRepository.save(a1);
        aportacionRepository.save(a2);
        aportacionRepository.save(a3);

        k1.addTipoAlimento(t1);

        k2.addTipoAlimento(t2);

        tipoAlimentoRepository.save(t1);

        tipoAlimentoRepository.save(t2);

        tipoAlimentoRepository.save(t3);

        Tiene ti = new Tiene(c1, t3, 10);
        Tiene ti1 = new Tiene(c1, t2, 5);

        c1.addTiene(ti);
        c1.addTiene(ti1);
        c1.addDestinatario(d1);

        cajaRepository.save(c1);
        tieneRepository.saveAll(List.of(ti,ti1));
        
    }
}
