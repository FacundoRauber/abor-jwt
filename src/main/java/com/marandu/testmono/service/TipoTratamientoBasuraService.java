package com.marandu.testmono.service;

import com.marandu.testmono.domain.TipoTratamientoBasura;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoTratamientoBasura}.
 */
public interface TipoTratamientoBasuraService {

    /**
     * Save a tipoTratamientoBasura.
     *
     * @param tipoTratamientoBasura the entity to save.
     * @return the persisted entity.
     */
    TipoTratamientoBasura save(TipoTratamientoBasura tipoTratamientoBasura);

    /**
     * Get all the tipoTratamientoBasuras.
     *
     * @return the list of entities.
     */
    List<TipoTratamientoBasura> findAll();


    /**
     * Get the "id" tipoTratamientoBasura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoTratamientoBasura> findOne(Long id);

    /**
     * Delete the "id" tipoTratamientoBasura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
