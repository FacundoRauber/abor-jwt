package com.marandu.testmono.repository;

import com.marandu.testmono.domain.Nacionalidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nacionalidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NacionalidadRepository extends JpaRepository<Nacionalidad, Long> {

}
