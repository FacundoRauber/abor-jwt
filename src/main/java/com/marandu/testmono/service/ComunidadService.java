package com.marandu.testmono.service;

import com.marandu.testmono.domain.Comunidad;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Comunidad}.
 */
public interface ComunidadService {

    /**
     * Save a comunidad.
     *
     * @param comunidad the entity to save.
     * @return the persisted entity.
     */
    Comunidad save(Comunidad comunidad);

    /**
     * Get all the comunidads.
     *
     * @return the list of entities.
     */
    List<Comunidad> findAll();


    /**
     * Get the "id" comunidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comunidad> findOne(Long id);

    /**
     * Delete the "id" comunidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
