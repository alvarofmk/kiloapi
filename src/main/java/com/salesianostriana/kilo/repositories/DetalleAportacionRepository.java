package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.DetalleAportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleAportacionRepository extends JpaRepository<DetalleAportacion, Long> {
}
