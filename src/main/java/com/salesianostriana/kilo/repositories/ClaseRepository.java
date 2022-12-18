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

    /*
    @Query(
            "SELECT new com.salesianostriana.kilo.dtos.clase.ClaseResponseDT0"
    )
    public Optional<ClaseResponseDTO> findFull(@Param("id") Long id)

     */

    @Query(nativeQuery = true)
    public ClaseResponseDTO findFull(Long id);
}
