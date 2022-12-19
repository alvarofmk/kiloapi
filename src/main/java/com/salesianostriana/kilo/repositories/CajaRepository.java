package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.Tiene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {



}
