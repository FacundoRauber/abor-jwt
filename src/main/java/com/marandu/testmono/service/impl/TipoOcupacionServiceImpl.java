package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.TipoOcupacionService;
import com.marandu.testmono.domain.TipoOcupacion;
import com.marandu.testmono.repository.TipoOcupacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoOcupacion}.
 */
@Service
@Transactional
public class TipoOcupacionServiceImpl implements TipoOcupacionService {

    private final Logger log = LoggerFactory.getLogger(TipoOcupacionServiceImpl.class);

    private final TipoOcupacionRepository tipoOcupacionRepository;

    public TipoOcupacionServiceImpl(TipoOcupacionRepository tipoOcupacionRepository) {
        this.tipoOcupacionRepository = tipoOcupacionRepository;
    }

    /**
     * Save a tipoOcupacion.
     *
     * @param tipoOcupacion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoOcupacion save(TipoOcupacion tipoOcupacion) {
        log.debug("Request to save TipoOcupacion : {}", tipoOcupacion);
        return tipoOcupacionRepository.save(tipoOcupacion);
    }

    /**
     * Get all the tipoOcupacions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoOcupacion> findAll() {
        log.debug("Request to get all TipoOcupacions");
        return tipoOcupacionRepository.findAll();
    }


    /**
     * Get one tipoOcupacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoOcupacion> findOne(Long id) {
        log.debug("Request to get TipoOcupacion : {}", id);
        return tipoOcupacionRepository.findById(id);
    }

    /**
     * Delete the tipoOcupacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoOcupacion : {}", id);
        tipoOcupacionRepository.deleteById(id);
    }
}
