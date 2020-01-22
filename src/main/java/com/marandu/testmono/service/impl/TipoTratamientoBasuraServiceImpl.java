package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.TipoTratamientoBasuraService;
import com.marandu.testmono.domain.TipoTratamientoBasura;
import com.marandu.testmono.repository.TipoTratamientoBasuraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoTratamientoBasura}.
 */
@Service
@Transactional
public class TipoTratamientoBasuraServiceImpl implements TipoTratamientoBasuraService {

    private final Logger log = LoggerFactory.getLogger(TipoTratamientoBasuraServiceImpl.class);

    private final TipoTratamientoBasuraRepository tipoTratamientoBasuraRepository;

    public TipoTratamientoBasuraServiceImpl(TipoTratamientoBasuraRepository tipoTratamientoBasuraRepository) {
        this.tipoTratamientoBasuraRepository = tipoTratamientoBasuraRepository;
    }

    /**
     * Save a tipoTratamientoBasura.
     *
     * @param tipoTratamientoBasura the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoTratamientoBasura save(TipoTratamientoBasura tipoTratamientoBasura) {
        log.debug("Request to save TipoTratamientoBasura : {}", tipoTratamientoBasura);
        return tipoTratamientoBasuraRepository.save(tipoTratamientoBasura);
    }

    /**
     * Get all the tipoTratamientoBasuras.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoTratamientoBasura> findAll() {
        log.debug("Request to get all TipoTratamientoBasuras");
        return tipoTratamientoBasuraRepository.findAll();
    }


    /**
     * Get one tipoTratamientoBasura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTratamientoBasura> findOne(Long id) {
        log.debug("Request to get TipoTratamientoBasura : {}", id);
        return tipoTratamientoBasuraRepository.findById(id);
    }

    /**
     * Delete the tipoTratamientoBasura by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoTratamientoBasura : {}", id);
        tipoTratamientoBasuraRepository.deleteById(id);
    }
}
