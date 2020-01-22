package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.TipoServicio;
import com.marandu.testmono.repository.TipoServicioRepository;
import com.marandu.testmono.service.TipoServicioService;
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
 * Integration tests for the {@link TipoServicioResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class TipoServicioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Autowired
    private TipoServicioService tipoServicioService;

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

    private MockMvc restTipoServicioMockMvc;

    private TipoServicio tipoServicio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoServicioResource tipoServicioResource = new TipoServicioResource(tipoServicioService);
        this.restTipoServicioMockMvc = MockMvcBuilders.standaloneSetup(tipoServicioResource)
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
    public static TipoServicio createEntity(EntityManager em) {
        TipoServicio tipoServicio = new TipoServicio()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return tipoServicio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoServicio createUpdatedEntity(EntityManager em) {
        TipoServicio tipoServicio = new TipoServicio()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return tipoServicio;
    }

    @BeforeEach
    public void initTest() {
        tipoServicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoServicio() throws Exception {
        int databaseSizeBeforeCreate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio
        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicio)))
            .andExpect(status().isCreated());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoServicio testTipoServicio = tipoServicioList.get(tipoServicioList.size() - 1);
        assertThat(testTipoServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoServicio.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createTipoServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio with an existing ID
        tipoServicio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoServicioRepository.findAll().size();
        // set the field null
        tipoServicio.setNombre(null);

        // Create the TipoServicio, which fails.

        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicio)))
            .andExpect(status().isBadRequest());

        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoServicios() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        // Get all the tipoServicioList
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        // Get the tipoServicio
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios/{id}", tipoServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoServicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoServicio() throws Exception {
        // Get the tipoServicio
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioService.save(tipoServicio);

        int databaseSizeBeforeUpdate = tipoServicioRepository.findAll().size();

        // Update the tipoServicio
        TipoServicio updatedTipoServicio = tipoServicioRepository.findById(tipoServicio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoServicio are not directly saved in db
        em.detach(updatedTipoServicio);
        updatedTipoServicio
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restTipoServicioMockMvc.perform(put("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoServicio)))
            .andExpect(status().isOk());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeUpdate);
        TipoServicio testTipoServicio = tipoServicioList.get(tipoServicioList.size() - 1);
        assertThat(testTipoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoServicio.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoServicio() throws Exception {
        int databaseSizeBeforeUpdate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoServicioMockMvc.perform(put("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicio)))
            .andExpect(status().isBadRequest());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioService.save(tipoServicio);

        int databaseSizeBeforeDelete = tipoServicioRepository.findAll().size();

        // Delete the tipoServicio
        restTipoServicioMockMvc.perform(delete("/api/tipo-servicios/{id}", tipoServicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
