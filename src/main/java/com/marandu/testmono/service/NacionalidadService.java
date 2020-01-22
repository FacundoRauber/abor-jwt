package com.marandu.testmono.service;

import com.marandu.testmono.domain.Nacionalidad;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Nacionalidad}.
 */
public interface NacionalidadService {

    /**
     * Save a nacionalidad.
     *
     * @param nacionalidad the entity to save.
     * @return the persisted entity.
     */
    Nacionalidad save(Nacionalidad nacionalidad);

    /**
     * Get all the nacionalidads.
     *
     * @return the list of entities.
     */
    List<Nacionalidad> findAll();


    /**
     * Get the "id" nacionalidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nacionalidad> findOne(Long id);

    /**
     * Delete the "id" nacionalidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
