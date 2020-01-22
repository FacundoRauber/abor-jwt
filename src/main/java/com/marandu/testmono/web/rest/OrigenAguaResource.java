package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.OrigenAgua;
import com.marandu.testmono.service.OrigenAguaService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.OrigenAgua}.
 */
@RestController
@RequestMapping("/api")
public class OrigenAguaResource {

    private final Logger log = LoggerFactory.getLogger(OrigenAguaResource.class);

    private static final String ENTITY_NAME = "origenAgua";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigenAguaService origenAguaService;

    public OrigenAguaResource(OrigenAguaService origenAguaService) {
        this.origenAguaService = origenAguaService;
    }

    /**
     * {@code POST  /origen-aguas} : Create a new origenAgua.
     *
     * @param origenAgua the origenAgua to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new origenAgua, or with status {@code 400 (Bad Request)} if the origenAgua has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origen-aguas")
    public ResponseEntity<OrigenAgua> createOrigenAgua(@Valid @RequestBody OrigenAgua origenAgua) throws URISyntaxException {
        log.debug("REST request to save OrigenAgua : {}", origenAgua);
        if (origenAgua.getId() != null) {
            throw new BadRequestAlertException("A new origenAgua cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigenAgua result = origenAguaService.save(origenAgua);
        return ResponseEntity.created(new URI("/api/origen-aguas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origen-aguas} : Updates an existing origenAgua.
     *
     * @param origenAgua the origenAgua to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated origenAgua,
     * or with status {@code 400 (Bad Request)} if the origenAgua is not valid,
     * or with status {@code 500 (Internal Server Error)} if the origenAgua couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origen-aguas")
    public ResponseEntity<OrigenAgua> updateOrigenAgua(@Valid @RequestBody OrigenAgua origenAgua) throws URISyntaxException {
        log.debug("REST request to update OrigenAgua : {}", origenAgua);
        if (origenAgua.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigenAgua result = origenAguaService.save(origenAgua);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, origenAgua.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origen-aguas} : get all the origenAguas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of origenAguas in body.
     */
    @GetMapping("/origen-aguas")
    public List<OrigenAgua> getAllOrigenAguas() {
        log.debug("REST request to get all OrigenAguas");
        return origenAguaService.findAll();
    }

    /**
     * {@code GET  /origen-aguas/:id} : get the "id" origenAgua.
     *
     * @param id the id of the origenAgua to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the origenAgua, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origen-aguas/{id}")
    public ResponseEntity<OrigenAgua> getOrigenAgua(@PathVariable Long id) {
        log.debug("REST request to get OrigenAgua : {}", id);
        Optional<OrigenAgua> origenAgua = origenAguaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(origenAgua);
    }

    /**
     * {@code DELETE  /origen-aguas/:id} : delete the "id" origenAgua.
     *
     * @param id the id of the origenAgua to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origen-aguas/{id}")
    public ResponseEntity<Void> deleteOrigenAgua(@PathVariable Long id) {
        log.debug("REST request to delete OrigenAgua : {}", id);
        origenAguaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
