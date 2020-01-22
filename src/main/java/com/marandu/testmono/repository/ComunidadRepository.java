package com.marandu.testmono.repository;

import com.marandu.testmono.domain.Comunidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Comunidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {

}
