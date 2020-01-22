package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.TipoViviendaService;
import com.marandu.testmono.domain.TipoVivienda;
import com.marandu.testmono.repository.TipoViviendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoVivienda}.
 */
@Service
@Transactional
public class TipoViviendaServiceImpl implements TipoViviendaService {

    private final Logger log = LoggerFactory.getLogger(TipoViviendaServiceImpl.class);

    private final TipoViviendaRepository tipoViviendaRepository;

    public TipoViviendaServiceImpl(TipoViviendaRepository tipoViviendaRepository) {
        this.tipoViviendaRepository = tipoViviendaRepository;
    }

    /**
     * Save a tipoVivienda.
     *
     * @param tipoVivienda the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoVivienda save(TipoVivienda tipoVivienda) {
        log.debug("Request to save TipoVivienda : {}", tipoVivienda);
        return tipoViviendaRepository.save(tipoVivienda);
    }

    /**
     * Get all the tipoViviendas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoVivienda> findAll() {
        log.debug("Request to get all TipoViviendas");
        return tipoViviendaRepository.findAll();
    }


    /**
     * Get one tipoVivienda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoVivienda> findOne(Long id) {
        log.debug("Request to get TipoVivienda : {}", id);
        return tipoViviendaRepository.findById(id);
    }

    /**
     * Delete the tipoVivienda by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoVivienda : {}", id);
        tipoViviendaRepository.deleteById(id);
    }
}
