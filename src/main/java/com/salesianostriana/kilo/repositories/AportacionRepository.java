package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
}
