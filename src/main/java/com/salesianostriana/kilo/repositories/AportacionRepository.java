package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
    @Query("""
            SELECT CASE WHEN da.tipoAlimento.id = :id THEN true ELSE false END
            FROM DetalleAportacion da
            """)
    public boolean tipoAlimentoInAportacion(@Param("id") Long id);
}
