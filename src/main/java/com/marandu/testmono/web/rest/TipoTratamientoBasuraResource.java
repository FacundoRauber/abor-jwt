package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.TipoTratamientoBasura;
import com.marandu.testmono.service.TipoTratamientoBasuraService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.TipoTratamientoBasura}.
 */
@RestController
@RequestMapping("/api")
public class TipoTratamientoBasuraResource {

    private final Logger log = LoggerFactory.getLogger(TipoTratamientoBasuraResource.class);

    private static final String ENTITY_NAME = "tipoTratamientoBasura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoTratamientoBasuraService tipoTratamientoBasuraService;

    public TipoTratamientoBasuraResource(TipoTratamientoBasuraService tipoTratamientoBasuraService) {
        this.tipoTratamientoBasuraService = tipoTratamientoBasuraService;
    }

    /**
     * {@code POST  /tipo-tratamiento-basuras} : Create a new tipoTratamientoBasura.
     *
     * @param tipoTratamientoBasura the tipoTratamientoBasura to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoTratamientoBasura, or with status {@code 400 (Bad Request)} if the tipoTratamientoBasura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-tratamiento-basuras")
    public ResponseEntity<TipoTratamientoBasura> createTipoTratamientoBasura(@Valid @RequestBody TipoTratamientoBasura tipoTratamientoBasura) throws URISyntaxException {
        log.debug("REST request to save TipoTratamientoBasura : {}", tipoTratamientoBasura);
        if (tipoTratamientoBasura.getId() != null) {
            throw new BadRequestAlertException("A new tipoTratamientoBasura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoTratamientoBasura result = tipoTratamientoBasuraService.save(tipoTratamientoBasura);
        return ResponseEntity.created(new URI("/api/tipo-tratamiento-basuras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-tratamiento-basuras} : Updates an existing tipoTratamientoBasura.
     *
     * @param tipoTratamientoBasura the tipoTratamientoBasura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTratamientoBasura,
     * or with status {@code 400 (Bad Request)} if the tipoTratamientoBasura is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoTratamientoBasura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-tratamiento-basuras")
    public ResponseEntity<TipoTratamientoBasura> updateTipoTratamientoBasura(@Valid @RequestBody TipoTratamientoBasura tipoTratamientoBasura) throws URISyntaxException {
        log.debug("REST request to update TipoTratamientoBasura : {}", tipoTratamientoBasura);
        if (tipoTratamientoBasura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoTratamientoBasura result = tipoTratamientoBasuraService.save(tipoTratamientoBasura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTratamientoBasura.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-tratamiento-basuras} : get all the tipoTratamientoBasuras.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoTratamientoBasuras in body.
     */
    @GetMapping("/tipo-tratamiento-basuras")
    public List<TipoTratamientoBasura> getAllTipoTratamientoBasuras() {
        log.debug("REST request to get all TipoTratamientoBasuras");
        return tipoTratamientoBasuraService.findAll();
    }

    /**
     * {@code GET  /tipo-tratamiento-basuras/:id} : get the "id" tipoTratamientoBasura.
     *
     * @param id the id of the tipoTratamientoBasura to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoTratamientoBasura, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-tratamiento-basuras/{id}")
    public ResponseEntity<TipoTratamientoBasura> getTipoTratamientoBasura(@PathVariable Long id) {
        log.debug("REST request to get TipoTratamientoBasura : {}", id);
        Optional<TipoTratamientoBasura> tipoTratamientoBasura = tipoTratamientoBasuraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoTratamientoBasura);
    }

    /**
     * {@code DELETE  /tipo-tratamiento-basuras/:id} : delete the "id" tipoTratamientoBasura.
     *
     * @param id the id of the tipoTratamientoBasura to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-tratamiento-basuras/{id}")
    public ResponseEntity<Void> deleteTipoTratamientoBasura(@PathVariable Long id) {
        log.debug("REST request to delete TipoTratamientoBasura : {}", id);
        tipoTratamientoBasuraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
