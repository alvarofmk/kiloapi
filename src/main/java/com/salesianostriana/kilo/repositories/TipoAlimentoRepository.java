package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {
}
