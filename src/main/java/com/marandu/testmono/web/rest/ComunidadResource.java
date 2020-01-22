package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.Comunidad;
import com.marandu.testmono.service.ComunidadService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.Comunidad}.
 */
@RestController
@RequestMapping("/api")
public class ComunidadResource {

    private final Logger log = LoggerFactory.getLogger(ComunidadResource.class);

    private static final String ENTITY_NAME = "comunidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunidadService comunidadService;

    public ComunidadResource(ComunidadService comunidadService) {
        this.comunidadService = comunidadService;
    }

    /**
     * {@code POST  /comunidads} : Create a new comunidad.
     *
     * @param comunidad the comunidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comunidad, or with status {@code 400 (Bad Request)} if the comunidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comunidads")
    public ResponseEntity<Comunidad> createComunidad(@Valid @RequestBody Comunidad comunidad) throws URISyntaxException {
        log.debug("REST request to save Comunidad : {}", comunidad);
        if (comunidad.getId() != null) {
            throw new BadRequestAlertException("A new comunidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comunidad result = comunidadService.save(comunidad);
        return ResponseEntity.created(new URI("/api/comunidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comunidads} : Updates an existing comunidad.
     *
     * @param comunidad the comunidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunidad,
     * or with status {@code 400 (Bad Request)} if the comunidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comunidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comunidads")
    public ResponseEntity<Comunidad> updateComunidad(@Valid @RequestBody Comunidad comunidad) throws URISyntaxException {
        log.debug("REST request to update Comunidad : {}", comunidad);
        if (comunidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comunidad result = comunidadService.save(comunidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comunidads} : get all the comunidads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunidads in body.
     */
    @GetMapping("/comunidads")
    public List<Comunidad> getAllComunidads() {
        log.debug("REST request to get all Comunidads");
        return comunidadService.findAll();
    }

    /**
     * {@code GET  /comunidads/:id} : get the "id" comunidad.
     *
     * @param id the id of the comunidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comunidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comunidads/{id}")
    public ResponseEntity<Comunidad> getComunidad(@PathVariable Long id) {
        log.debug("REST request to get Comunidad : {}", id);
        Optional<Comunidad> comunidad = comunidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comunidad);
    }

    /**
     * {@code DELETE  /comunidads/:id} : delete the "id" comunidad.
     *
     * @param id the id of the comunidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comunidads/{id}")
    public ResponseEntity<Void> deleteComunidad(@PathVariable Long id) {
        log.debug("REST request to delete Comunidad : {}", id);
        comunidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
