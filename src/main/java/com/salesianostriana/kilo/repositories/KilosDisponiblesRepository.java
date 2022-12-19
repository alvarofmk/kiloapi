package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.dtos.kilos_disponibles.KilosDisponiblesDTO;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, Long> {

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.kilos_disponibles.KilosDisponiblesDTO(ta.id, ta.nombre, kd.cantidadDisponible)
            FROM KilosDisponibles kd JOIN TipoAlimento ta ON ta.id = kd.tipoAlimento
            """)
    List<KilosDisponiblesDTO> getAllKgDisponibles();

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.kilos_disponibles.KilosDisponiblesDTO( ta.nombre, kd.cantidadDisponible)
            FROM KilosDisponibles kd JOIN TipoAlimento ta ON ta.id = kd.tipoAlimento WHERE ta.id = :id
            """)
    public KilosDisponiblesDTO getAllKgDisponiblesById(@Param("id") Long id);
    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO(d.detalleAportacionPK.aportacionId, d.detalleAportacionPK.lineaId, d.cantidadKg)
            FROM DetalleAportacion d WHERE d.tipoAlimento.id = :id
            """)
    List<DetallesAportacionResponseDTO> getKgPorAlimentos(@Param("id") Long id);
}
