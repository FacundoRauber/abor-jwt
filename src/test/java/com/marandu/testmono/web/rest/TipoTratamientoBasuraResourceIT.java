package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.TipoTratamientoBasura;
import com.marandu.testmono.repository.TipoTratamientoBasuraRepository;
import com.marandu.testmono.service.TipoTratamientoBasuraService;
import com.marandu.testmono.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.marandu.testmono.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoTratamientoBasuraResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class TipoTratamientoBasuraResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private TipoTratamientoBasuraRepository tipoTratamientoBasuraRepository;

    @Autowired
    private TipoTratamientoBasuraService tipoTratamientoBasuraService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoTratamientoBasuraMockMvc;

    private TipoTratamientoBasura tipoTratamientoBasura;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoTratamientoBasuraResource tipoTratamientoBasuraResource = new TipoTratamientoBasuraResource(tipoTratamientoBasuraService);
        this.restTipoTratamientoBasuraMockMvc = MockMvcBuilders.standaloneSetup(tipoTratamientoBasuraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTratamientoBasura createEntity(EntityManager em) {
        TipoTratamientoBasura tipoTratamientoBasura = new TipoTratamientoBasura()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return tipoTratamientoBasura;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTratamientoBasura createUpdatedEntity(EntityManager em) {
        TipoTratamientoBasura tipoTratamientoBasura = new TipoTratamientoBasura()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return tipoTratamientoBasura;
    }

    @BeforeEach
    public void initTest() {
        tipoTratamientoBasura = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoTratamientoBasura() throws Exception {
        int databaseSizeBeforeCreate = tipoTratamientoBasuraRepository.findAll().size();

        // Create the TipoTratamientoBasura
        restTipoTratamientoBasuraMockMvc.perform(post("/api/tipo-tratamiento-basuras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTratamientoBasura)))
            .andExpect(status().isCreated());

        // Validate the TipoTratamientoBasura in the database
        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoTratamientoBasura testTipoTratamientoBasura = tipoTratamientoBasuraList.get(tipoTratamientoBasuraList.size() - 1);
        assertThat(testTipoTratamientoBasura.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoTratamientoBasura.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createTipoTratamientoBasuraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoTratamientoBasuraRepository.findAll().size();

        // Create the TipoTratamientoBasura with an existing ID
        tipoTratamientoBasura.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoTratamientoBasuraMockMvc.perform(post("/api/tipo-tratamiento-basuras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTratamientoBasura)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTratamientoBasura in the database
        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoTratamientoBasuraRepository.findAll().size();
        // set the field null
        tipoTratamientoBasura.setNombre(null);

        // Create the TipoTratamientoBasura, which fails.

        restTipoTratamientoBasuraMockMvc.perform(post("/api/tipo-tratamiento-basuras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTratamientoBasura)))
            .andExpect(status().isBadRequest());

        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoTratamientoBasuras() throws Exception {
        // Initialize the database
        tipoTratamientoBasuraRepository.saveAndFlush(tipoTratamientoBasura);

        // Get all the tipoTratamientoBasuraList
        restTipoTratamientoBasuraMockMvc.perform(get("/api/tipo-tratamiento-basuras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTratamientoBasura.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoTratamientoBasura() throws Exception {
        // Initialize the database
        tipoTratamientoBasuraRepository.saveAndFlush(tipoTratamientoBasura);

        // Get the tipoTratamientoBasura
        restTipoTratamientoBasuraMockMvc.perform(get("/api/tipo-tratamiento-basuras/{id}", tipoTratamientoBasura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoTratamientoBasura.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoTratamientoBasura() throws Exception {
        // Get the tipoTratamientoBasura
        restTipoTratamientoBasuraMockMvc.perform(get("/api/tipo-tratamiento-basuras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoTratamientoBasura() throws Exception {
        // Initialize the database
        tipoTratamientoBasuraService.save(tipoTratamientoBasura);

        int databaseSizeBeforeUpdate = tipoTratamientoBasuraRepository.findAll().size();

        // Update the tipoTratamientoBasura
        TipoTratamientoBasura updatedTipoTratamientoBasura = tipoTratamientoBasuraRepository.findById(tipoTratamientoBasura.getId()).get();
        // Disconnect from session so that the updates on updatedTipoTratamientoBasura are not directly saved in db
        em.detach(updatedTipoTratamientoBasura);
        updatedTipoTratamientoBasura
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restTipoTratamientoBasuraMockMvc.perform(put("/api/tipo-tratamiento-basuras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoTratamientoBasura)))
            .andExpect(status().isOk());

        // Validate the TipoTratamientoBasura in the database
        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeUpdate);
        TipoTratamientoBasura testTipoTratamientoBasura = tipoTratamientoBasuraList.get(tipoTratamientoBasuraList.size() - 1);
        assertThat(testTipoTratamientoBasura.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoTratamientoBasura.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoTratamientoBasura() throws Exception {
        int databaseSizeBeforeUpdate = tipoTratamientoBasuraRepository.findAll().size();

        // Create the TipoTratamientoBasura

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTratamientoBasuraMockMvc.perform(put("/api/tipo-tratamiento-basuras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoTratamientoBasura)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTratamientoBasura in the database
        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoTratamientoBasura() throws Exception {
        // Initialize the database
        tipoTratamientoBasuraService.save(tipoTratamientoBasura);

        int databaseSizeBeforeDelete = tipoTratamientoBasuraRepository.findAll().size();

        // Delete the tipoTratamientoBasura
        restTipoTratamientoBasuraMockMvc.perform(delete("/api/tipo-tratamiento-basuras/{id}", tipoTratamientoBasura.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoTratamientoBasura> tipoTratamientoBasuraList = tipoTratamientoBasuraRepository.findAll();
        assertThat(tipoTratamientoBasuraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
