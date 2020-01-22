package com.marandu.testmono.repository;

import com.marandu.testmono.domain.OrigenAgua;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrigenAgua entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrigenAguaRepository extends JpaRepository<OrigenAgua, Long> {

}
