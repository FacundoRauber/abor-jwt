package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.NivelEducativoService;
import com.marandu.testmono.domain.NivelEducativo;
import com.marandu.testmono.repository.NivelEducativoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NivelEducativo}.
 */
@Service
@Transactional
public class NivelEducativoServiceImpl implements NivelEducativoService {

    private final Logger log = LoggerFactory.getLogger(NivelEducativoServiceImpl.class);

    private final NivelEducativoRepository nivelEducativoRepository;

    public NivelEducativoServiceImpl(NivelEducativoRepository nivelEducativoRepository) {
        this.nivelEducativoRepository = nivelEducativoRepository;
    }

    /**
     * Save a nivelEducativo.
     *
     * @param nivelEducativo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NivelEducativo save(NivelEducativo nivelEducativo) {
        log.debug("Request to save NivelEducativo : {}", nivelEducativo);
        return nivelEducativoRepository.save(nivelEducativo);
    }

    /**
     * Get all the nivelEducativos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<NivelEducativo> findAll() {
        log.debug("Request to get all NivelEducativos");
        return nivelEducativoRepository.findAll();
    }


    /**
     * Get one nivelEducativo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NivelEducativo> findOne(Long id) {
        log.debug("Request to get NivelEducativo : {}", id);
        return nivelEducativoRepository.findById(id);
    }

    /**
     * Delete the nivelEducativo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NivelEducativo : {}", id);
        nivelEducativoRepository.deleteById(id);
    }
}
