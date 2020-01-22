package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.VinculoFamiliarService;
import com.marandu.testmono.domain.VinculoFamiliar;
import com.marandu.testmono.repository.VinculoFamiliarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VinculoFamiliar}.
 */
@Service
@Transactional
public class VinculoFamiliarServiceImpl implements VinculoFamiliarService {

    private final Logger log = LoggerFactory.getLogger(VinculoFamiliarServiceImpl.class);

    private final VinculoFamiliarRepository vinculoFamiliarRepository;

    public VinculoFamiliarServiceImpl(VinculoFamiliarRepository vinculoFamiliarRepository) {
        this.vinculoFamiliarRepository = vinculoFamiliarRepository;
    }

    /**
     * Save a vinculoFamiliar.
     *
     * @param vinculoFamiliar the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VinculoFamiliar save(VinculoFamiliar vinculoFamiliar) {
        log.debug("Request to save VinculoFamiliar : {}", vinculoFamiliar);
        return vinculoFamiliarRepository.save(vinculoFamiliar);
    }

    /**
     * Get all the vinculoFamiliars.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VinculoFamiliar> findAll() {
        log.debug("Request to get all VinculoFamiliars");
        return vinculoFamiliarRepository.findAll();
    }


    /**
     * Get one vinculoFamiliar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VinculoFamiliar> findOne(Long id) {
        log.debug("Request to get VinculoFamiliar : {}", id);
        return vinculoFamiliarRepository.findById(id);
    }

    /**
     * Delete the vinculoFamiliar by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VinculoFamiliar : {}", id);
        vinculoFamiliarRepository.deleteById(id);
    }
}
