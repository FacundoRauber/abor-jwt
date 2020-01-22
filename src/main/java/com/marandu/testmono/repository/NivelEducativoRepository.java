package com.marandu.testmono.repository;

import com.marandu.testmono.domain.NivelEducativo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NivelEducativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Long> {

}
