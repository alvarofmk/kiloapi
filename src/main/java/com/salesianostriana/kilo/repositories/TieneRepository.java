package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.entities.Tiene;
import com.salesianostriana.kilo.entities.keys.TienePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TieneRepository extends JpaRepository<Tiene, TienePK> {

}
