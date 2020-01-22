package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.NivelEducativo;
import com.marandu.testmono.service.NivelEducativoService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.NivelEducativo}.
 */
@RestController
@RequestMapping("/api")
public class NivelEducativoResource {

    private final Logger log = LoggerFactory.getLogger(NivelEducativoResource.class);

    private static final String ENTITY_NAME = "nivelEducativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelEducativoService nivelEducativoService;

    public NivelEducativoResource(NivelEducativoService nivelEducativoService) {
        this.nivelEducativoService = nivelEducativoService;
    }

    /**
     * {@code POST  /nivel-educativos} : Create a new nivelEducativo.
     *
     * @param nivelEducativo the nivelEducativo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelEducativo, or with status {@code 400 (Bad Request)} if the nivelEducativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-educativos")
    public ResponseEntity<NivelEducativo> createNivelEducativo(@Valid @RequestBody NivelEducativo nivelEducativo) throws URISyntaxException {
        log.debug("REST request to save NivelEducativo : {}", nivelEducativo);
        if (nivelEducativo.getId() != null) {
            throw new BadRequestAlertException("A new nivelEducativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelEducativo result = nivelEducativoService.save(nivelEducativo);
        return ResponseEntity.created(new URI("/api/nivel-educativos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-educativos} : Updates an existing nivelEducativo.
     *
     * @param nivelEducativo the nivelEducativo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelEducativo,
     * or with status {@code 400 (Bad Request)} if the nivelEducativo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelEducativo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-educativos")
    public ResponseEntity<NivelEducativo> updateNivelEducativo(@Valid @RequestBody NivelEducativo nivelEducativo) throws URISyntaxException {
        log.debug("REST request to update NivelEducativo : {}", nivelEducativo);
        if (nivelEducativo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelEducativo result = nivelEducativoService.save(nivelEducativo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelEducativo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nivel-educativos} : get all the nivelEducativos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelEducativos in body.
     */
    @GetMapping("/nivel-educativos")
    public List<NivelEducativo> getAllNivelEducativos() {
        log.debug("REST request to get all NivelEducativos");
        return nivelEducativoService.findAll();
    }

    /**
     * {@code GET  /nivel-educativos/:id} : get the "id" nivelEducativo.
     *
     * @param id the id of the nivelEducativo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelEducativo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-educativos/{id}")
    public ResponseEntity<NivelEducativo> getNivelEducativo(@PathVariable Long id) {
        log.debug("REST request to get NivelEducativo : {}", id);
        Optional<NivelEducativo> nivelEducativo = nivelEducativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelEducativo);
    }

    /**
     * {@code DELETE  /nivel-educativos/:id} : delete the "id" nivelEducativo.
     *
     * @param id the id of the nivelEducativo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-educativos/{id}")
    public ResponseEntity<Void> deleteNivelEducativo(@PathVariable Long id) {
        log.debug("REST request to delete NivelEducativo : {}", id);
        nivelEducativoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
