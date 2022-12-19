package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.clase.ClaseResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.clase.ClaseResponseDTO(c.id, c.nombre, c.tutor, 
            (SELECT COUNT from a WHERE a.clase = c), 
            (SELECT SUM (det.cantidadKgs) WHERE det.aportacion.id = a.id)) 
            
            FROM Clase c 
            JOIN Aportacion a ON c.id = a.clase 
            JOIN DetalleAportacion det ON a.id = det.aportacion
            
            WHERE c.id = :id
            """)
    Optional<ClaseResponseDTO> findFull(@Param("id") Long id);
}
