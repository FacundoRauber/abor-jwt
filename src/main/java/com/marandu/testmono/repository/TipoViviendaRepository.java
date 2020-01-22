package com.marandu.testmono.repository;

import com.marandu.testmono.domain.TipoVivienda;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoVivienda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoViviendaRepository extends JpaRepository<TipoVivienda, Long> {

}
