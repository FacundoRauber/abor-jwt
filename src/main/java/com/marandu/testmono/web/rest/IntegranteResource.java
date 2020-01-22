package com.marandu.testmono.web.rest;

import com.marandu.testmono.domain.Integrante;
import com.marandu.testmono.service.IntegranteService;
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
 * REST controller for managing {@link com.marandu.testmono.domain.Integrante}.
 */
@RestController
@RequestMapping("/api")
public class IntegranteResource {

    private final Logger log = LoggerFactory.getLogger(IntegranteResource.class);

    private static final String ENTITY_NAME = "integrante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegranteService integranteService;

    public IntegranteResource(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    /**
     * {@code POST  /integrantes} : Create a new integrante.
     *
     * @param integrante the integrante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrante, or with status {@code 400 (Bad Request)} if the integrante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integrantes")
    public ResponseEntity<Integrante> createIntegrante(@Valid @RequestBody Integrante integrante) throws URISyntaxException {
        log.debug("REST request to save Integrante : {}", integrante);
        if (integrante.getId() != null) {
            throw new BadRequestAlertException("A new integrante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Integrante result = integranteService.save(integrante);
        return ResponseEntity.created(new URI("/api/integrantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integrantes} : Updates an existing integrante.
     *
     * @param integrante the integrante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrante,
     * or with status {@code 400 (Bad Request)} if the integrante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integrantes")
    public ResponseEntity<Integrante> updateIntegrante(@Valid @RequestBody Integrante integrante) throws URISyntaxException {
        log.debug("REST request to update Integrante : {}", integrante);
        if (integrante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Integrante result = integranteService.save(integrante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /integrantes} : get all the integrantes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrantes in body.
     */
    @GetMapping("/integrantes")
    public ResponseEntity<List<Integrante>> getAllIntegrantes(Pageable pageable) {
        log.debug("REST request to get a page of Integrantes");
        Page<Integrante> page = integranteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /integrantes/:id} : get the "id" integrante.
     *
     * @param id the id of the integrante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrantes/{id}")
    public ResponseEntity<Integrante> getIntegrante(@PathVariable Long id) {
        log.debug("REST request to get Integrante : {}", id);
        Optional<Integrante> integrante = integranteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrante);
    }

    /**
     * {@code DELETE  /integrantes/:id} : delete the "id" integrante.
     *
     * @param id the id of the integrante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integrantes/{id}")
    public ResponseEntity<Void> deleteIntegrante(@PathVariable Long id) {
        log.debug("REST request to delete Integrante : {}", id);
        integranteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
