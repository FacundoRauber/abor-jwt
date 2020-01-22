package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.IntegranteService;
import com.marandu.testmono.domain.Integrante;
import com.marandu.testmono.repository.IntegranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Integrante}.
 */
@Service
@Transactional
public class IntegranteServiceImpl implements IntegranteService {

    private final Logger log = LoggerFactory.getLogger(IntegranteServiceImpl.class);

    private final IntegranteRepository integranteRepository;

    public IntegranteServiceImpl(IntegranteRepository integranteRepository) {
        this.integranteRepository = integranteRepository;
    }

    /**
     * Save a integrante.
     *
     * @param integrante the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Integrante save(Integrante integrante) {
        log.debug("Request to save Integrante : {}", integrante);
        return integranteRepository.save(integrante);
    }

    /**
     * Get all the integrantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Integrante> findAll(Pageable pageable) {
        log.debug("Request to get all Integrantes");
        return integranteRepository.findAll(pageable);
    }


    /**
     * Get one integrante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Integrante> findOne(Long id) {
        log.debug("Request to get Integrante : {}", id);
        return integranteRepository.findById(id);
    }

    /**
     * Delete the integrante by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Integrante : {}", id);
        integranteRepository.deleteById(id);
    }
}
