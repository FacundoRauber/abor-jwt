package com.marandu.testmono.repository;

import com.marandu.testmono.domain.TipoOcupacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoOcupacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoOcupacionRepository extends JpaRepository<TipoOcupacion, Long> {

}
