package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.TipoPlanAsistenciaService;
import com.marandu.testmono.domain.TipoPlanAsistencia;
import com.marandu.testmono.repository.TipoPlanAsistenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoPlanAsistencia}.
 */
@Service
@Transactional
public class TipoPlanAsistenciaServiceImpl implements TipoPlanAsistenciaService {

    private final Logger log = LoggerFactory.getLogger(TipoPlanAsistenciaServiceImpl.class);

    private final TipoPlanAsistenciaRepository tipoPlanAsistenciaRepository;

    public TipoPlanAsistenciaServiceImpl(TipoPlanAsistenciaRepository tipoPlanAsistenciaRepository) {
        this.tipoPlanAsistenciaRepository = tipoPlanAsistenciaRepository;
    }

    /**
     * Save a tipoPlanAsistencia.
     *
     * @param tipoPlanAsistencia the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoPlanAsistencia save(TipoPlanAsistencia tipoPlanAsistencia) {
        log.debug("Request to save TipoPlanAsistencia : {}", tipoPlanAsistencia);
        return tipoPlanAsistenciaRepository.save(tipoPlanAsistencia);
    }

    /**
     * Get all the tipoPlanAsistencias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoPlanAsistencia> findAll() {
        log.debug("Request to get all TipoPlanAsistencias");
        return tipoPlanAsistenciaRepository.findAll();
    }


    /**
     * Get one tipoPlanAsistencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPlanAsistencia> findOne(Long id) {
        log.debug("Request to get TipoPlanAsistencia : {}", id);
        return tipoPlanAsistenciaRepository.findById(id);
    }

    /**
     * Delete the tipoPlanAsistencia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPlanAsistencia : {}", id);
        tipoPlanAsistenciaRepository.deleteById(id);
    }
}
