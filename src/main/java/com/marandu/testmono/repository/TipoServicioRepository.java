package com.marandu.testmono.repository;

import com.marandu.testmono.domain.TipoServicio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoServicio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, Long> {

}
