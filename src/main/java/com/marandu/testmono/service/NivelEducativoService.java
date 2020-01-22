package com.marandu.testmono.service;

import com.marandu.testmono.domain.NivelEducativo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NivelEducativo}.
 */
public interface NivelEducativoService {

    /**
     * Save a nivelEducativo.
     *
     * @param nivelEducativo the entity to save.
     * @return the persisted entity.
     */
    NivelEducativo save(NivelEducativo nivelEducativo);

    /**
     * Get all the nivelEducativos.
     *
     * @return the list of entities.
     */
    List<NivelEducativo> findAll();


    /**
     * Get the "id" nivelEducativo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NivelEducativo> findOne(Long id);

    /**
     * Delete the "id" nivelEducativo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
