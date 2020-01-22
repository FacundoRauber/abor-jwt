package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.OrigenEnergia;
import com.marandu.testmono.service.OrigenEnergiaService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.OrigenEnergia}.
 */
@RestController
@RequestMapping("/api")
public class OrigenEnergiaResource {

    private final Logger log = LoggerFactory.getLogger(OrigenEnergiaResource.class);

    private static final String ENTITY_NAME = "origenEnergia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigenEnergiaService origenEnergiaService;

    public OrigenEnergiaResource(OrigenEnergiaService origenEnergiaService) {
        this.origenEnergiaService = origenEnergiaService;
    }

    /**
     * {@code POST  /origen-energias} : Create a new origenEnergia.
     *
     * @param origenEnergia the origenEnergia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new origenEnergia, or with status {@code 400 (Bad Request)} if the origenEnergia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origen-energias")
    public ResponseEntity<OrigenEnergia> createOrigenEnergia(@Valid @RequestBody OrigenEnergia origenEnergia) throws URISyntaxException {
        log.debug("REST request to save OrigenEnergia : {}", origenEnergia);
        if (origenEnergia.getId() != null) {
            throw new BadRequestAlertException("A new origenEnergia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigenEnergia result = origenEnergiaService.save(origenEnergia);
        return ResponseEntity.created(new URI("/api/origen-energias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origen-energias} : Updates an existing origenEnergia.
     *
     * @param origenEnergia the origenEnergia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated origenEnergia,
     * or with status {@code 400 (Bad Request)} if the origenEnergia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the origenEnergia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origen-energias")
    public ResponseEntity<OrigenEnergia> updateOrigenEnergia(@Valid @RequestBody OrigenEnergia origenEnergia) throws URISyntaxException {
        log.debug("REST request to update OrigenEnergia : {}", origenEnergia);
        if (origenEnergia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigenEnergia result = origenEnergiaService.save(origenEnergia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, origenEnergia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origen-energias} : get all the origenEnergias.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of origenEnergias in body.
     */
    @GetMapping("/origen-energias")
    public List<OrigenEnergia> getAllOrigenEnergias() {
        log.debug("REST request to get all OrigenEnergias");
        return origenEnergiaService.findAll();
    }

    /**
     * {@code GET  /origen-energias/:id} : get the "id" origenEnergia.
     *
     * @param id the id of the origenEnergia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the origenEnergia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origen-energias/{id}")
    public ResponseEntity<OrigenEnergia> getOrigenEnergia(@PathVariable Long id) {
        log.debug("REST request to get OrigenEnergia : {}", id);
        Optional<OrigenEnergia> origenEnergia = origenEnergiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(origenEnergia);
    }

    /**
     * {@code DELETE  /origen-energias/:id} : delete the "id" origenEnergia.
     *
     * @param id the id of the origenEnergia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origen-energias/{id}")
    public ResponseEntity<Void> deleteOrigenEnergia(@PathVariable Long id) {
        log.debug("REST request to delete OrigenEnergia : {}", id);
        origenEnergiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
