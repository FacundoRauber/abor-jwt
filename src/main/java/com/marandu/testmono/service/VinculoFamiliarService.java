package com.marandu.testmono.service;

import com.marandu.testmono.domain.VinculoFamiliar;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VinculoFamiliar}.
 */
public interface VinculoFamiliarService {

    /**
     * Save a vinculoFamiliar.
     *
     * @param vinculoFamiliar the entity to save.
     * @return the persisted entity.
     */
    VinculoFamiliar save(VinculoFamiliar vinculoFamiliar);

    /**
     * Get all the vinculoFamiliars.
     *
     * @return the list of entities.
     */
    List<VinculoFamiliar> findAll();


    /**
     * Get the "id" vinculoFamiliar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VinculoFamiliar> findOne(Long id);

    /**
     * Delete the "id" vinculoFamiliar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
