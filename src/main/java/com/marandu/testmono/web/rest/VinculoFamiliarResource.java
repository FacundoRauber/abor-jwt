package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.VinculoFamiliar;
import com.marandu.testmono.service.VinculoFamiliarService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.VinculoFamiliar}.
 */
@RestController
@RequestMapping("/api")
public class VinculoFamiliarResource {

    private final Logger log = LoggerFactory.getLogger(VinculoFamiliarResource.class);

    private static final String ENTITY_NAME = "vinculoFamiliar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VinculoFamiliarService vinculoFamiliarService;

    public VinculoFamiliarResource(VinculoFamiliarService vinculoFamiliarService) {
        this.vinculoFamiliarService = vinculoFamiliarService;
    }

    /**
     * {@code POST  /vinculo-familiars} : Create a new vinculoFamiliar.
     *
     * @param vinculoFamiliar the vinculoFamiliar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vinculoFamiliar, or with status {@code 400 (Bad Request)} if the vinculoFamiliar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vinculo-familiars")
    public ResponseEntity<VinculoFamiliar> createVinculoFamiliar(@Valid @RequestBody VinculoFamiliar vinculoFamiliar) throws URISyntaxException {
        log.debug("REST request to save VinculoFamiliar : {}", vinculoFamiliar);
        if (vinculoFamiliar.getId() != null) {
            throw new BadRequestAlertException("A new vinculoFamiliar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VinculoFamiliar result = vinculoFamiliarService.save(vinculoFamiliar);
        return ResponseEntity.created(new URI("/api/vinculo-familiars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vinculo-familiars} : Updates an existing vinculoFamiliar.
     *
     * @param vinculoFamiliar the vinculoFamiliar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculoFamiliar,
     * or with status {@code 400 (Bad Request)} if the vinculoFamiliar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vinculoFamiliar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vinculo-familiars")
    public ResponseEntity<VinculoFamiliar> updateVinculoFamiliar(@Valid @RequestBody VinculoFamiliar vinculoFamiliar) throws URISyntaxException {
        log.debug("REST request to update VinculoFamiliar : {}", vinculoFamiliar);
        if (vinculoFamiliar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VinculoFamiliar result = vinculoFamiliarService.save(vinculoFamiliar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vinculoFamiliar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vinculo-familiars} : get all the vinculoFamiliars.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vinculoFamiliars in body.
     */
    @GetMapping("/vinculo-familiars")
    public List<VinculoFamiliar> getAllVinculoFamiliars() {
        log.debug("REST request to get all VinculoFamiliars");
        return vinculoFamiliarService.findAll();
    }

    /**
     * {@code GET  /vinculo-familiars/:id} : get the "id" vinculoFamiliar.
     *
     * @param id the id of the vinculoFamiliar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vinculoFamiliar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vinculo-familiars/{id}")
    public ResponseEntity<VinculoFamiliar> getVinculoFamiliar(@PathVariable Long id) {
        log.debug("REST request to get VinculoFamiliar : {}", id);
        Optional<VinculoFamiliar> vinculoFamiliar = vinculoFamiliarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vinculoFamiliar);
    }

    /**
     * {@code DELETE  /vinculo-familiars/:id} : delete the "id" vinculoFamiliar.
     *
     * @param id the id of the vinculoFamiliar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vinculo-familiars/{id}")
    public ResponseEntity<Void> deleteVinculoFamiliar(@PathVariable Long id) {
        log.debug("REST request to delete VinculoFamiliar : {}", id);
        vinculoFamiliarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
