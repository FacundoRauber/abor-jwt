package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.OrigenEnergiaService;
import com.marandu.testmono.domain.OrigenEnergia;
import com.marandu.testmono.repository.OrigenEnergiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrigenEnergia}.
 */
@Service
@Transactional
public class OrigenEnergiaServiceImpl implements OrigenEnergiaService {

    private final Logger log = LoggerFactory.getLogger(OrigenEnergiaServiceImpl.class);

    private final OrigenEnergiaRepository origenEnergiaRepository;

    public OrigenEnergiaServiceImpl(OrigenEnergiaRepository origenEnergiaRepository) {
        this.origenEnergiaRepository = origenEnergiaRepository;
    }

    /**
     * Save a origenEnergia.
     *
     * @param origenEnergia the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrigenEnergia save(OrigenEnergia origenEnergia) {
        log.debug("Request to save OrigenEnergia : {}", origenEnergia);
        return origenEnergiaRepository.save(origenEnergia);
    }

    /**
     * Get all the origenEnergias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrigenEnergia> findAll() {
        log.debug("Request to get all OrigenEnergias");
        return origenEnergiaRepository.findAll();
    }


    /**
     * Get one origenEnergia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrigenEnergia> findOne(Long id) {
        log.debug("Request to get OrigenEnergia : {}", id);
        return origenEnergiaRepository.findById(id);
    }

    /**
     * Delete the origenEnergia by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrigenEnergia : {}", id);
        origenEnergiaRepository.deleteById(id);
    }
}
