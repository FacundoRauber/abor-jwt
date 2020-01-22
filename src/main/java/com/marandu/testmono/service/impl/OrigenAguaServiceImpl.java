package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.OrigenAguaService;
import com.marandu.testmono.domain.OrigenAgua;
import com.marandu.testmono.repository.OrigenAguaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrigenAgua}.
 */
@Service
@Transactional
public class OrigenAguaServiceImpl implements OrigenAguaService {

    private final Logger log = LoggerFactory.getLogger(OrigenAguaServiceImpl.class);

    private final OrigenAguaRepository origenAguaRepository;

    public OrigenAguaServiceImpl(OrigenAguaRepository origenAguaRepository) {
        this.origenAguaRepository = origenAguaRepository;
    }

    /**
     * Save a origenAgua.
     *
     * @param origenAgua the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrigenAgua save(OrigenAgua origenAgua) {
        log.debug("Request to save OrigenAgua : {}", origenAgua);
        return origenAguaRepository.save(origenAgua);
    }

    /**
     * Get all the origenAguas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrigenAgua> findAll() {
        log.debug("Request to get all OrigenAguas");
        return origenAguaRepository.findAll();
    }


    /**
     * Get one origenAgua by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrigenAgua> findOne(Long id) {
        log.debug("Request to get OrigenAgua : {}", id);
        return origenAguaRepository.findById(id);
    }

    /**
     * Delete the origenAgua by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrigenAgua : {}", id);
        origenAguaRepository.deleteById(id);
    }
}
