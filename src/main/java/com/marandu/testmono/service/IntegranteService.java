package com.marandu.testmono.service;

import com.marandu.testmono.domain.Integrante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Integrante}.
 */
public interface IntegranteService {

    /**
     * Save a integrante.
     *
     * @param integrante the entity to save.
     * @return the persisted entity.
     */
    Integrante save(Integrante integrante);

    /**
     * Get all the integrantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Integrante> findAll(Pageable pageable);


    /**
     * Get the "id" integrante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Integrante> findOne(Long id);

    /**
     * Delete the "id" integrante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
