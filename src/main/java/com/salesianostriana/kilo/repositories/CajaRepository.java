package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

}
