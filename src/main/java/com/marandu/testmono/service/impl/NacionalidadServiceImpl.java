package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.NacionalidadService;
import com.marandu.testmono.domain.Nacionalidad;
import com.marandu.testmono.repository.NacionalidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Nacionalidad}.
 */
@Service
@Transactional
public class NacionalidadServiceImpl implements NacionalidadService {

    private final Logger log = LoggerFactory.getLogger(NacionalidadServiceImpl.class);

    private final NacionalidadRepository nacionalidadRepository;

    public NacionalidadServiceImpl(NacionalidadRepository nacionalidadRepository) {
        this.nacionalidadRepository = nacionalidadRepository;
    }

    /**
     * Save a nacionalidad.
     *
     * @param nacionalidad the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Nacionalidad save(Nacionalidad nacionalidad) {
        log.debug("Request to save Nacionalidad : {}", nacionalidad);
        return nacionalidadRepository.save(nacionalidad);
    }

    /**
     * Get all the nacionalidads.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Nacionalidad> findAll() {
        log.debug("Request to get all Nacionalidads");
        return nacionalidadRepository.findAll();
    }


    /**
     * Get one nacionalidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Nacionalidad> findOne(Long id) {
        log.debug("Request to get Nacionalidad : {}", id);
        return nacionalidadRepository.findById(id);
    }

    /**
     * Delete the nacionalidad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nacionalidad : {}", id);
        nacionalidadRepository.deleteById(id);
    }
}
