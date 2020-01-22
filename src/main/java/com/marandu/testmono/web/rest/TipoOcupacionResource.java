package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.TipoOcupacion;
import com.marandu.testmono.service.TipoOcupacionService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.TipoOcupacion}.
 */
@RestController
@RequestMapping("/api")
public class TipoOcupacionResource {

    private final Logger log = LoggerFactory.getLogger(TipoOcupacionResource.class);

    private static final String ENTITY_NAME = "tipoOcupacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoOcupacionService tipoOcupacionService;

    public TipoOcupacionResource(TipoOcupacionService tipoOcupacionService) {
        this.tipoOcupacionService = tipoOcupacionService;
    }

    /**
     * {@code POST  /tipo-ocupacions} : Create a new tipoOcupacion.
     *
     * @param tipoOcupacion the tipoOcupacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoOcupacion, or with status {@code 400 (Bad Request)} if the tipoOcupacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-ocupacions")
    public ResponseEntity<TipoOcupacion> createTipoOcupacion(@Valid @RequestBody TipoOcupacion tipoOcupacion) throws URISyntaxException {
        log.debug("REST request to save TipoOcupacion : {}", tipoOcupacion);
        if (tipoOcupacion.getId() != null) {
            throw new BadRequestAlertException("A new tipoOcupacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoOcupacion result = tipoOcupacionService.save(tipoOcupacion);
        return ResponseEntity.created(new URI("/api/tipo-ocupacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-ocupacions} : Updates an existing tipoOcupacion.
     *
     * @param tipoOcupacion the tipoOcupacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoOcupacion,
     * or with status {@code 400 (Bad Request)} if the tipoOcupacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoOcupacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-ocupacions")
    public ResponseEntity<TipoOcupacion> updateTipoOcupacion(@Valid @RequestBody TipoOcupacion tipoOcupacion) throws URISyntaxException {
        log.debug("REST request to update TipoOcupacion : {}", tipoOcupacion);
        if (tipoOcupacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoOcupacion result = tipoOcupacionService.save(tipoOcupacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoOcupacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-ocupacions} : get all the tipoOcupacions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoOcupacions in body.
     */
    @GetMapping("/tipo-ocupacions")
    public List<TipoOcupacion> getAllTipoOcupacions() {
        log.debug("REST request to get all TipoOcupacions");
        return tipoOcupacionService.findAll();
    }

    /**
     * {@code GET  /tipo-ocupacions/:id} : get the "id" tipoOcupacion.
     *
     * @param id the id of the tipoOcupacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoOcupacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-ocupacions/{id}")
    public ResponseEntity<TipoOcupacion> getTipoOcupacion(@PathVariable Long id) {
        log.debug("REST request to get TipoOcupacion : {}", id);
        Optional<TipoOcupacion> tipoOcupacion = tipoOcupacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoOcupacion);
    }

    /**
     * {@code DELETE  /tipo-ocupacions/:id} : delete the "id" tipoOcupacion.
     *
     * @param id the id of the tipoOcupacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-ocupacions/{id}")
    public ResponseEntity<Void> deleteTipoOcupacion(@PathVariable Long id) {
        log.debug("REST request to delete TipoOcupacion : {}", id);
        tipoOcupacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
