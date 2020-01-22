package com.marandu.testmono.repository;

import com.marandu.testmono.domain.Relevamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Relevamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelevamientoRepository extends JpaRepository<Relevamiento, Long> {

}
