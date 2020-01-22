package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.RelevamientoService;
import com.marandu.testmono.domain.Relevamiento;
import com.marandu.testmono.repository.RelevamientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Relevamiento}.
 */
@Service
@Transactional
public class RelevamientoServiceImpl implements RelevamientoService {

    private final Logger log = LoggerFactory.getLogger(RelevamientoServiceImpl.class);

    private final RelevamientoRepository relevamientoRepository;

    public RelevamientoServiceImpl(RelevamientoRepository relevamientoRepository) {
        this.relevamientoRepository = relevamientoRepository;
    }

    /**
     * Save a relevamiento.
     *
     * @param relevamiento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Relevamiento save(Relevamiento relevamiento) {
        log.debug("Request to save Relevamiento : {}", relevamiento);
        return relevamientoRepository.save(relevamiento);
    }

    /**
     * Get all the relevamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Relevamiento> findAll(Pageable pageable) {
        log.debug("Request to get all Relevamientos");
        return relevamientoRepository.findAll(pageable);
    }


    /**
     * Get one relevamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Relevamiento> findOne(Long id) {
        log.debug("Request to get Relevamiento : {}", id);
        return relevamientoRepository.findById(id);
    }

    /**
     * Delete the relevamiento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Relevamiento : {}", id);
        relevamientoRepository.deleteById(id);
    }
}
