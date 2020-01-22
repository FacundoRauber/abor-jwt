package com.marandu.testmono.repository;

import com.marandu.testmono.domain.VinculoFamiliar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VinculoFamiliar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VinculoFamiliarRepository extends JpaRepository<VinculoFamiliar, Long> {

}
