package com.marandu.testmono.repository;

import com.marandu.testmono.domain.OrigenEnergia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrigenEnergia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrigenEnergiaRepository extends JpaRepository<OrigenEnergia, Long> {

}
