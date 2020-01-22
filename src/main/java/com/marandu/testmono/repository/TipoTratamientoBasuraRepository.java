package com.marandu.testmono.repository;

import com.marandu.testmono.domain.TipoTratamientoBasura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoTratamientoBasura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoTratamientoBasuraRepository extends JpaRepository<TipoTratamientoBasura, Long> {

}
