package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.Relevamiento;
import com.marandu.testmono.service.RelevamientoService;
import com.marandu.testmono.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.marandu.testmono.domain.Relevamiento}.
 */
@RestController
@RequestMapping("/api")
public class RelevamientoResource {

    private final Logger log = LoggerFactory.getLogger(RelevamientoResource.class);

    private static final String ENTITY_NAME = "relevamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelevamientoService relevamientoService;

    public RelevamientoResource(RelevamientoService relevamientoService) {
        this.relevamientoService = relevamientoService;
    }

    /**
     * {@code POST  /relevamientos} : Create a new relevamiento.
     *
     * @param relevamiento the relevamiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relevamiento, or with status {@code 400 (Bad Request)} if the relevamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relevamientos")
    public ResponseEntity<Relevamiento> createRelevamiento(@Valid @RequestBody Relevamiento relevamiento) throws URISyntaxException {
        log.debug("REST request to save Relevamiento : {}", relevamiento);
        if (relevamiento.getId() != null) {
            throw new BadRequestAlertException("A new relevamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relevamiento result = relevamientoService.save(relevamiento);
        return ResponseEntity.created(new URI("/api/relevamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relevamientos} : Updates an existing relevamiento.
     *
     * @param relevamiento the relevamiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relevamiento,
     * or with status {@code 400 (Bad Request)} if the relevamiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relevamiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relevamientos")
    public ResponseEntity<Relevamiento> updateRelevamiento(@Valid @RequestBody Relevamiento relevamiento) throws URISyntaxException {
        log.debug("REST request to update Relevamiento : {}", relevamiento);
        if (relevamiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Relevamiento result = relevamientoService.save(relevamiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relevamiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relevamientos} : get all the relevamientos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relevamientos in body.
     */
    @GetMapping("/relevamientos")
    public ResponseEntity<List<Relevamiento>> getAllRelevamientos(Pageable pageable) {
        log.debug("REST request to get a page of Relevamientos");
        Page<Relevamiento> page = relevamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relevamientos/:id} : get the "id" relevamiento.
     *
     * @param id the id of the relevamiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relevamiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relevamientos/{id}")
    public ResponseEntity<Relevamiento> getRelevamiento(@PathVariable Long id) {
        log.debug("REST request to get Relevamiento : {}", id);
        Optional<Relevamiento> relevamiento = relevamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relevamiento);
    }

    /**
     * {@code DELETE  /relevamientos/:id} : delete the "id" relevamiento.
     *
     * @param id the id of the relevamiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relevamientos/{id}")
    public ResponseEntity<Void> deleteRelevamiento(@PathVariable Long id) {
        log.debug("REST request to delete Relevamiento : {}", id);
        relevamientoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
