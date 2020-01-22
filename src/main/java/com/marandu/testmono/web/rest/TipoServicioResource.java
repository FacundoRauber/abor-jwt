package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.TipoServicio;
import com.marandu.testmono.service.TipoServicioService;
import com.marandu.testmono.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.marandu.testmono.domain.TipoServicio}.
 */
@RestController
@RequestMapping("/api")
public class TipoServicioResource {

    private final Logger log = LoggerFactory.getLogger(TipoServicioResource.class);

    private static final String ENTITY_NAME = "tipoServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoServicioService tipoServicioService;

    public TipoServicioResource(TipoServicioService tipoServicioService) {
        this.tipoServicioService = tipoServicioService;
    }

    /**
     * {@code POST  /tipo-servicios} : Create a new tipoServicio.
     *
     * @param tipoServicio the tipoServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoServicio, or with status {@code 400 (Bad Request)} if the tipoServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-servicios")
    public ResponseEntity<TipoServicio> createTipoServicio(@Valid @RequestBody TipoServicio tipoServicio) throws URISyntaxException {
        log.debug("REST request to save TipoServicio : {}", tipoServicio);
        if (tipoServicio.getId() != null) {
            throw new BadRequestAlertException("A new tipoServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoServicio result = tipoServicioService.save(tipoServicio);
        return ResponseEntity.created(new URI("/api/tipo-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-servicios} : Updates an existing tipoServicio.
     *
     * @param tipoServicio the tipoServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoServicio,
     * or with status {@code 400 (Bad Request)} if the tipoServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-servicios")
    public ResponseEntity<TipoServicio> updateTipoServicio(@Valid @RequestBody TipoServicio tipoServicio) throws URISyntaxException {
        log.debug("REST request to update TipoServicio : {}", tipoServicio);
        if (tipoServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoServicio result = tipoServicioService.save(tipoServicio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-servicios} : get all the tipoServicios.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoServicios in body.
     */
    @GetMapping("/tipo-servicios")
    public List<TipoServicio> getAllTipoServicios() {
        log.debug("REST request to get all TipoServicios");
        return tipoServicioService.findAll();
    }

    /**
     * {@code GET  /tipo-servicios/:id} : get the "id" tipoServicio.
     *
     * @param id the id of the tipoServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-servicios/{id}")
    public ResponseEntity<TipoServicio> getTipoServicio(@PathVariable Long id) {
        log.debug("REST request to get TipoServicio : {}", id);
        Optional<TipoServicio> tipoServicio = tipoServicioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoServicio);
    }

    /**
     * {@code DELETE  /tipo-servicios/:id} : delete the "id" tipoServicio.
     *
     * @param id the id of the tipoServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-servicios/{id}")
    public ResponseEntity<Void> deleteTipoServicio(@PathVariable Long id) {
        log.debug("REST request to delete TipoServicio : {}", id);
        tipoServicioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
