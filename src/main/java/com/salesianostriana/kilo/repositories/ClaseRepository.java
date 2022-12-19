package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.RankQueryResponseDTO;
import com.salesianostriana.kilo.dtos.RankingResponseDTO;
import com.salesianostriana.kilo.dtos.clase.ClaseQueryResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("SELECT new com.salesianostriana.kilo.dtos.RankQueryResponseDTO(a.clase.id, a.clase.nombre, SUM(d.cantidadKg) AS cantidadPorAp) " +
            "FROM Aportacion a JOIN DetalleAportacion d ON a.id = d.aportacion GROUP BY a.id")
    public List<RankQueryResponseDTO> findClasesOrderedByRank();

    @Query("SELECT new com.salesianostriana.kilo.dtos.clase.ClaseQueryResponseDTO(c.id, c.nombre) FROM Clase c")
    public List<ClaseQueryResponseDTO> findClassReferences();

}
