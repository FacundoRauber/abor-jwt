package com.marandu.testmono.service;

import com.marandu.testmono.domain.Relevamiento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Relevamiento}.
 */
public interface RelevamientoService {

    /**
     * Save a relevamiento.
     *
     * @param relevamiento the entity to save.
     * @return the persisted entity.
     */
    Relevamiento save(Relevamiento relevamiento);

    /**
     * Get all the relevamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Relevamiento> findAll(Pageable pageable);


    /**
     * Get the "id" relevamiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Relevamiento> findOne(Long id);

    /**
     * Delete the "id" relevamiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
