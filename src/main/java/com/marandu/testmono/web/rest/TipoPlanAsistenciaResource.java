package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.TipoPlanAsistencia;
import com.marandu.testmono.service.TipoPlanAsistenciaService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.TipoPlanAsistencia}.
 */
@RestController
@RequestMapping("/api")
public class TipoPlanAsistenciaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPlanAsistenciaResource.class);

    private static final String ENTITY_NAME = "tipoPlanAsistencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPlanAsistenciaService tipoPlanAsistenciaService;

    public TipoPlanAsistenciaResource(TipoPlanAsistenciaService tipoPlanAsistenciaService) {
        this.tipoPlanAsistenciaService = tipoPlanAsistenciaService;
    }

    /**
     * {@code POST  /tipo-plan-asistencias} : Create a new tipoPlanAsistencia.
     *
     * @param tipoPlanAsistencia the tipoPlanAsistencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPlanAsistencia, or with status {@code 400 (Bad Request)} if the tipoPlanAsistencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-plan-asistencias")
    public ResponseEntity<TipoPlanAsistencia> createTipoPlanAsistencia(@Valid @RequestBody TipoPlanAsistencia tipoPlanAsistencia) throws URISyntaxException {
        log.debug("REST request to save TipoPlanAsistencia : {}", tipoPlanAsistencia);
        if (tipoPlanAsistencia.getId() != null) {
            throw new BadRequestAlertException("A new tipoPlanAsistencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPlanAsistencia result = tipoPlanAsistenciaService.save(tipoPlanAsistencia);
        return ResponseEntity.created(new URI("/api/tipo-plan-asistencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-plan-asistencias} : Updates an existing tipoPlanAsistencia.
     *
     * @param tipoPlanAsistencia the tipoPlanAsistencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPlanAsistencia,
     * or with status {@code 400 (Bad Request)} if the tipoPlanAsistencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPlanAsistencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-plan-asistencias")
    public ResponseEntity<TipoPlanAsistencia> updateTipoPlanAsistencia(@Valid @RequestBody TipoPlanAsistencia tipoPlanAsistencia) throws URISyntaxException {
        log.debug("REST request to update TipoPlanAsistencia : {}", tipoPlanAsistencia);
        if (tipoPlanAsistencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPlanAsistencia result = tipoPlanAsistenciaService.save(tipoPlanAsistencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPlanAsistencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-plan-asistencias} : get all the tipoPlanAsistencias.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPlanAsistencias in body.
     */
    @GetMapping("/tipo-plan-asistencias")
    public List<TipoPlanAsistencia> getAllTipoPlanAsistencias() {
        log.debug("REST request to get all TipoPlanAsistencias");
        return tipoPlanAsistenciaService.findAll();
    }

    /**
     * {@code GET  /tipo-plan-asistencias/:id} : get the "id" tipoPlanAsistencia.
     *
     * @param id the id of the tipoPlanAsistencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPlanAsistencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-plan-asistencias/{id}")
    public ResponseEntity<TipoPlanAsistencia> getTipoPlanAsistencia(@PathVariable Long id) {
        log.debug("REST request to get TipoPlanAsistencia : {}", id);
        Optional<TipoPlanAsistencia> tipoPlanAsistencia = tipoPlanAsistenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPlanAsistencia);
    }

    /**
     * {@code DELETE  /tipo-plan-asistencias/:id} : delete the "id" tipoPlanAsistencia.
     *
     * @param id the id of the tipoPlanAsistencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-plan-asistencias/{id}")
    public ResponseEntity<Void> deleteTipoPlanAsistencia(@PathVariable Long id) {
        log.debug("REST request to delete TipoPlanAsistencia : {}", id);
        tipoPlanAsistenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
