package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

}
