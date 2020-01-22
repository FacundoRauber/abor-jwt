package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.ComunidadService;
import com.marandu.testmono.domain.Comunidad;
import com.marandu.testmono.repository.ComunidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Comunidad}.
 */
@Service
@Transactional
public class ComunidadServiceImpl implements ComunidadService {

    private final Logger log = LoggerFactory.getLogger(ComunidadServiceImpl.class);

    private final ComunidadRepository comunidadRepository;

    public ComunidadServiceImpl(ComunidadRepository comunidadRepository) {
        this.comunidadRepository = comunidadRepository;
    }

    /**
     * Save a comunidad.
     *
     * @param comunidad the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Comunidad save(Comunidad comunidad) {
        log.debug("Request to save Comunidad : {}", comunidad);
        return comunidadRepository.save(comunidad);
    }

    /**
     * Get all the comunidads.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comunidad> findAll() {
        log.debug("Request to get all Comunidads");
        return comunidadRepository.findAll();
    }


    /**
     * Get one comunidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Comunidad> findOne(Long id) {
        log.debug("Request to get Comunidad : {}", id);
        return comunidadRepository.findById(id);
    }

    /**
     * Delete the comunidad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comunidad : {}", id);
        comunidadRepository.deleteById(id);
    }
}
