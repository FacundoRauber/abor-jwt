package com.marandu.testmono.service;

import com.marandu.testmono.domain.TipoVivienda;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoVivienda}.
 */
public interface TipoViviendaService {

    /**
     * Save a tipoVivienda.
     *
     * @param tipoVivienda the entity to save.
     * @return the persisted entity.
     */
    TipoVivienda save(TipoVivienda tipoVivienda);

    /**
     * Get all the tipoViviendas.
     *
     * @return the list of entities.
     */
    List<TipoVivienda> findAll();


    /**
     * Get the "id" tipoVivienda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoVivienda> findOne(Long id);

    /**
     * Delete the "id" tipoVivienda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
