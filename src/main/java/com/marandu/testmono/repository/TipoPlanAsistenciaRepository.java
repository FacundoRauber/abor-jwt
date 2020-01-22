package com.marandu.testmono.repository;

import com.marandu.testmono.domain.TipoPlanAsistencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoPlanAsistencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPlanAsistenciaRepository extends JpaRepository<TipoPlanAsistencia, Long> {

}
