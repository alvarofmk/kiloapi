package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.DestinatarioResponseDTO;
import com.salesianostriana.kilo.entities.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

    @Query("SELECT new com.salesianostriana.kilo.dtos.DestinatarioResponseDTO(d.nombre, d.direccion, d.personaContacto, d.telefono, COALESCE(COUNT(c.id), 0), COALESCE(SUM(c.kilosTotales), 0.0)) FROM Destinatario d LEFT JOIN Caja c ON d.id = c.destinatario WHERE d.id = :id GROUP BY d.id")
    public Optional<DestinatarioResponseDTO> findSummary(@Param("id") Long id);

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.DestinatarioResponseDTO(d.nombre, d.direccion, d.personaContacto, d.telefono, SUM(c.kilosTotales))
            FROM Destinatario d LEFT JOIN Caja c ON d.id = c.destinatario 
            GROUP BY d.id
            """)
    List<DestinatarioResponseDTO> getDestinatariosConCajas();
}
