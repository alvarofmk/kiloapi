package com.salesianostriana.kilo.repositories;
import com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("""
               SELECT SUM (det.cantidadKg) 
               FROM Clase c
               JOIN Aportacion a ON c.id = a.clase
               JOIN DetalleAportacion det ON a.id = det.aportacion
               JOIN TipoAlimento ta ON  det.tipoAlimento = ta.id
               WHERE c.id = :idClase
            """)
    Double findKilos(@Param("idClase") Long idCLase);



    @Query("SELECT new com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO(a.clase.id, a.clase.nombre, SUM(d.cantidadKg) AS cantidadPorAp) " +
            "FROM Aportacion a JOIN DetalleAportacion d ON a.id = d.aportacion GROUP BY a.id")
    public List<RankQueryResponseDTO> findClasesOrderedByRank();



}
