package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
}
