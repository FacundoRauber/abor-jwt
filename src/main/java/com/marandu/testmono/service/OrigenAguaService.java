package com.marandu.testmono.service;

import com.marandu.testmono.domain.OrigenAgua;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrigenAgua}.
 */
public interface OrigenAguaService {

    /**
     * Save a origenAgua.
     *
     * @param origenAgua the entity to save.
     * @return the persisted entity.
     */
    OrigenAgua save(OrigenAgua origenAgua);

    /**
     * Get all the origenAguas.
     *
     * @return the list of entities.
     */
    List<OrigenAgua> findAll();


    /**
     * Get the "id" origenAgua.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrigenAgua> findOne(Long id);

    /**
     * Delete the "id" origenAgua.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
