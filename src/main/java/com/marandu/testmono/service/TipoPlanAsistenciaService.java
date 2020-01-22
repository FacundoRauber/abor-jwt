package com.marandu.testmono.service;

import com.marandu.testmono.domain.TipoPlanAsistencia;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoPlanAsistencia}.
 */
public interface TipoPlanAsistenciaService {

    /**
     * Save a tipoPlanAsistencia.
     *
     * @param tipoPlanAsistencia the entity to save.
     * @return the persisted entity.
     */
    TipoPlanAsistencia save(TipoPlanAsistencia tipoPlanAsistencia);

    /**
     * Get all the tipoPlanAsistencias.
     *
     * @return the list of entities.
     */
    List<TipoPlanAsistencia> findAll();


    /**
     * Get the "id" tipoPlanAsistencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPlanAsistencia> findOne(Long id);

    /**
     * Delete the "id" tipoPlanAsistencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
