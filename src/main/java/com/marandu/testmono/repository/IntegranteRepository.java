package com.marandu.testmono.repository;

import com.marandu.testmono.domain.Integrante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Integrante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {

}
