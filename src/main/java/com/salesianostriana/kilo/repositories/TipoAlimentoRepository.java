package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {


    @Query("""
            SELECT CASE 
                WHEN COALESCE(t.tipoAlimento.id, 0) = :id THEN 1
                ELSE 0 END
            FROM Tiene t
            """)
    List<Integer> tipoAlimentoInTiene(@Param("id") Long id);

    @Query("SELECT a FROM TipoAlimento a JOIN KilosDisponibles k ON a.id = k.tipoAlimento JOIN Tiene t ON t.tienePK.tipoAlimentoId = k.tipoAlimento WHERE t.tienePK.tipoAlimentoId = a.id AND k.cantidadDisponible = 0")
    List<TipoAlimento> findAlimentosEmpaquetados();
}
