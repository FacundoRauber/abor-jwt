package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.Nacionalidad;
import com.marandu.testmono.service.NacionalidadService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.Nacionalidad}.
 */
@RestController
@RequestMapping("/api")
public class NacionalidadResource {

    private final Logger log = LoggerFactory.getLogger(NacionalidadResource.class);

    private static final String ENTITY_NAME = "nacionalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NacionalidadService nacionalidadService;

    public NacionalidadResource(NacionalidadService nacionalidadService) {
        this.nacionalidadService = nacionalidadService;
    }

    /**
     * {@code POST  /nacionalidads} : Create a new nacionalidad.
     *
     * @param nacionalidad the nacionalidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nacionalidad, or with status {@code 400 (Bad Request)} if the nacionalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nacionalidads")
    public ResponseEntity<Nacionalidad> createNacionalidad(@Valid @RequestBody Nacionalidad nacionalidad) throws URISyntaxException {
        log.debug("REST request to save Nacionalidad : {}", nacionalidad);
        if (nacionalidad.getId() != null) {
            throw new BadRequestAlertException("A new nacionalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nacionalidad result = nacionalidadService.save(nacionalidad);
        return ResponseEntity.created(new URI("/api/nacionalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nacionalidads} : Updates an existing nacionalidad.
     *
     * @param nacionalidad the nacionalidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nacionalidad,
     * or with status {@code 400 (Bad Request)} if the nacionalidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nacionalidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nacionalidads")
    public ResponseEntity<Nacionalidad> updateNacionalidad(@Valid @RequestBody Nacionalidad nacionalidad) throws URISyntaxException {
        log.debug("REST request to update Nacionalidad : {}", nacionalidad);
        if (nacionalidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nacionalidad result = nacionalidadService.save(nacionalidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nacionalidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nacionalidads} : get all the nacionalidads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nacionalidads in body.
     */
    @GetMapping("/nacionalidads")
    public List<Nacionalidad> getAllNacionalidads() {
        log.debug("REST request to get all Nacionalidads");
        return nacionalidadService.findAll();
    }

    /**
     * {@code GET  /nacionalidads/:id} : get the "id" nacionalidad.
     *
     * @param id the id of the nacionalidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nacionalidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nacionalidads/{id}")
    public ResponseEntity<Nacionalidad> getNacionalidad(@PathVariable Long id) {
        log.debug("REST request to get Nacionalidad : {}", id);
        Optional<Nacionalidad> nacionalidad = nacionalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nacionalidad);
    }

    /**
     * {@code DELETE  /nacionalidads/:id} : delete the "id" nacionalidad.
     *
     * @param id the id of the nacionalidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nacionalidads/{id}")
    public ResponseEntity<Void> deleteNacionalidad(@PathVariable Long id) {
        log.debug("REST request to delete Nacionalidad : {}", id);
        nacionalidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
