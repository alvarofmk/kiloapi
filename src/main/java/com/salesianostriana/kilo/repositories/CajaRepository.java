package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Caja;
import com.salesianostriana.kilo.entities.Tiene;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    @Query("""
            SELECT CASE WHEN t.tipoAlimento.id = :id THEN true ELSE false END
            FROM Tiene t
            """)
    public boolean tipoAlimentoInTiene(@Param("id") Long id);

}
