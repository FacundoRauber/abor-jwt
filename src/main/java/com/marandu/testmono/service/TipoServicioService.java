package com.marandu.testmono.service;

import com.marandu.testmono.domain.TipoServicio;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoServicio}.
 */
public interface TipoServicioService {

    /**
     * Save a tipoServicio.
     *
     * @param tipoServicio the entity to save.
     * @return the persisted entity.
     */
    TipoServicio save(TipoServicio tipoServicio);

    /**
     * Get all the tipoServicios.
     *
     * @return the list of entities.
     */
    List<TipoServicio> findAll();


    /**
     * Get the "id" tipoServicio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoServicio> findOne(Long id);

    /**
     * Delete the "id" tipoServicio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
