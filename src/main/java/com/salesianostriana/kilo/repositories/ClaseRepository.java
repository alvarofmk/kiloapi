package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.RankingResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("SELECT new com.salesianostriana.kilo.dtos.RankingResponseDTO(c.nombre, COUNT(a.id) as cantidad_aportaciones, AVG(a.cantidadPorAp) as media_kilos, SUM(a.cantidadPorAp) as kilos_aportados) FROM Clase c JOIN " +
            "(SELECT a2.id as subid, SUM(d2.cantidadKg) AS cantidadPorAp FROM Aportacion a2 JOIN DetalleAportacion d2 ON a2.id = d2.aportacion GROUP BY a2.id)" +
            " a ON c.id = a.subid GROUP BY c.id ORDER BY kilos_aportados")
    public RankingResponseDTO findClasesOrderedByRank();

}
