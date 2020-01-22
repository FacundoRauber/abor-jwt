package com.marandu.testmono.service.impl;

import com.marandu.testmono.service.TipoServicioService;
import com.marandu.testmono.domain.TipoServicio;
import com.marandu.testmono.repository.TipoServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoServicio}.
 */
@Service
@Transactional
public class TipoServicioServiceImpl implements TipoServicioService {

    private final Logger log = LoggerFactory.getLogger(TipoServicioServiceImpl.class);

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioServiceImpl(TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    /**
     * Save a tipoServicio.
     *
     * @param tipoServicio the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoServicio save(TipoServicio tipoServicio) {
        log.debug("Request to save TipoServicio : {}", tipoServicio);
        return tipoServicioRepository.save(tipoServicio);
    }

    /**
     * Get all the tipoServicios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoServicio> findAll() {
        log.debug("Request to get all TipoServicios");
        return tipoServicioRepository.findAll();
    }


    /**
     * Get one tipoServicio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoServicio> findOne(Long id) {
        log.debug("Request to get TipoServicio : {}", id);
        return tipoServicioRepository.findById(id);
    }

    /**
     * Delete the tipoServicio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoServicio : {}", id);
        tipoServicioRepository.deleteById(id);
    }
}
