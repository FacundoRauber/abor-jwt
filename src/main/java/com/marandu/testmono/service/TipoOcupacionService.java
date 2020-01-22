package com.marandu.testmono.service;

import com.marandu.testmono.domain.TipoOcupacion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoOcupacion}.
 */
public interface TipoOcupacionService {

    /**
     * Save a tipoOcupacion.
     *
     * @param tipoOcupacion the entity to save.
     * @return the persisted entity.
     */
    TipoOcupacion save(TipoOcupacion tipoOcupacion);

    /**
     * Get all the tipoOcupacions.
     *
     * @return the list of entities.
     */
    List<TipoOcupacion> findAll();


    /**
     * Get the "id" tipoOcupacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoOcupacion> findOne(Long id);

    /**
     * Delete the "id" tipoOcupacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
