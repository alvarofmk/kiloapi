package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

    @Query("""
            SELECT COALESCE(SUM(da.cantidadKg), 0.0)
            FROM DetalleAportacion da JOIN TipoAlimento ta ON ta.id = da.tipoAlimento
            WHERE ta.id = :id
            """)
    double getCantidadTotalKg(@Param("id") Long id);

    @Query("""
            SELECT CASE 
                WHEN COALESCE(t.tipoAlimento.id, 0) = :id THEN 1
                ELSE 0 END
            FROM Tiene t
            """)
    List<Integer> tipoAlimentoInTiene(@Param("id") Long id);
}
