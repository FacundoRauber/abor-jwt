package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.TipoVivienda;
import com.marandu.testmono.service.TipoViviendaService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.TipoVivienda}.
 */
@RestController
@RequestMapping("/api")
public class TipoViviendaResource {

    private final Logger log = LoggerFactory.getLogger(TipoViviendaResource.class);

    private static final String ENTITY_NAME = "tipoVivienda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoViviendaService tipoViviendaService;

    public TipoViviendaResource(TipoViviendaService tipoViviendaService) {
        this.tipoViviendaService = tipoViviendaService;
    }

    /**
     * {@code POST  /tipo-viviendas} : Create a new tipoVivienda.
     *
     * @param tipoVivienda the tipoVivienda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoVivienda, or with status {@code 400 (Bad Request)} if the tipoVivienda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-viviendas")
    public ResponseEntity<TipoVivienda> createTipoVivienda(@Valid @RequestBody TipoVivienda tipoVivienda) throws URISyntaxException {
        log.debug("REST request to save TipoVivienda : {}", tipoVivienda);
        if (tipoVivienda.getId() != null) {
            throw new BadRequestAlertException("A new tipoVivienda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoVivienda result = tipoViviendaService.save(tipoVivienda);
        return ResponseEntity.created(new URI("/api/tipo-viviendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-viviendas} : Updates an existing tipoVivienda.
     *
     * @param tipoVivienda the tipoVivienda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoVivienda,
     * or with status {@code 400 (Bad Request)} if the tipoVivienda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoVivienda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-viviendas")
    public ResponseEntity<TipoVivienda> updateTipoVivienda(@Valid @RequestBody TipoVivienda tipoVivienda) throws URISyntaxException {
        log.debug("REST request to update TipoVivienda : {}", tipoVivienda);
        if (tipoVivienda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoVivienda result = tipoViviendaService.save(tipoVivienda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoVivienda.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-viviendas} : get all the tipoViviendas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoViviendas in body.
     */
    @GetMapping("/tipo-viviendas")
    public List<TipoVivienda> getAllTipoViviendas() {
        log.debug("REST request to get all TipoViviendas");
        return tipoViviendaService.findAll();
    }

    /**
     * {@code GET  /tipo-viviendas/:id} : get the "id" tipoVivienda.
     *
     * @param id the id of the tipoVivienda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoVivienda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-viviendas/{id}")
    public ResponseEntity<TipoVivienda> getTipoVivienda(@PathVariable Long id) {
        log.debug("REST request to get TipoVivienda : {}", id);
        Optional<TipoVivienda> tipoVivienda = tipoViviendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoVivienda);
    }

    /**
     * {@code DELETE  /tipo-viviendas/:id} : delete the "id" tipoVivienda.
     *
     * @param id the id of the tipoVivienda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-viviendas/{id}")
    public ResponseEntity<Void> deleteTipoVivienda(@PathVariable Long id) {
        log.debug("REST request to delete TipoVivienda : {}", id);
        tipoViviendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
