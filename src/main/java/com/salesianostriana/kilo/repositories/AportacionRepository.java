package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
    @Query("""
            SELECT CASE 
                WHEN da.tipoAlimento.id = :id THEN 1 
                ELSE 0 END
            FROM DetalleAportacion da
            """)
    List<Integer> tipoAlimentoInAportacion(@Param("id") Long id);
}
