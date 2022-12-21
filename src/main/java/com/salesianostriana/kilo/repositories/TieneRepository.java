package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Tiene;
import com.salesianostriana.kilo.entities.keys.TienePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TieneRepository extends JpaRepository<Tiene, TienePK> {

    @Query("""
            SELECT t
            FROM Tiene t 
            WHERE t.tipoAlimento.id = :idAlim AND t.caja.id =:idCaja
            """)
    Tiene findOneTiene(@Param("idCaja") Long idCaja, @Param("idAlim") Long idAlim);

}
