package com.marandu.testmono.service;

import com.marandu.testmono.domain.OrigenEnergia;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OrigenEnergia}.
 */
public interface OrigenEnergiaService {

    /**
     * Save a origenEnergia.
     *
     * @param origenEnergia the entity to save.
     * @return the persisted entity.
     */
    OrigenEnergia save(OrigenEnergia origenEnergia);

    /**
     * Get all the origenEnergias.
     *
     * @return the list of entities.
     */
    List<OrigenEnergia> findAll();


    /**
     * Get the "id" origenEnergia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrigenEnergia> findOne(Long id);

    /**
     * Delete the "id" origenEnergia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
