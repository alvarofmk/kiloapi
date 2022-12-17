package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

    @Query("""
            SELECT COALESCE(SUM(da.cantidadKg), 0.0)
            FROM DetalleAportacion da JOIN TipoAlimento ta ON ta.id = da.tipoAlimento
            WHERE ta.id = :id
            """)
    public double getCantidadTotalKg(@Param("id") Long id);
}
