package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.TipoVivienda;
import com.marandu.testmono.repository.TipoViviendaRepository;
import com.marandu.testmono.service.TipoViviendaService;
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
 * Integration tests for the {@link TipoViviendaResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class TipoViviendaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private TipoViviendaRepository tipoViviendaRepository;

    @Autowired
    private TipoViviendaService tipoViviendaService;

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

    private MockMvc restTipoViviendaMockMvc;

    private TipoVivienda tipoVivienda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoViviendaResource tipoViviendaResource = new TipoViviendaResource(tipoViviendaService);
        this.restTipoViviendaMockMvc = MockMvcBuilders.standaloneSetup(tipoViviendaResource)
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
    public static TipoVivienda createEntity(EntityManager em) {
        TipoVivienda tipoVivienda = new TipoVivienda()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return tipoVivienda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoVivienda createUpdatedEntity(EntityManager em) {
        TipoVivienda tipoVivienda = new TipoVivienda()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return tipoVivienda;
    }

    @BeforeEach
    public void initTest() {
        tipoVivienda = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoVivienda() throws Exception {
        int databaseSizeBeforeCreate = tipoViviendaRepository.findAll().size();

        // Create the TipoVivienda
        restTipoViviendaMockMvc.perform(post("/api/tipo-viviendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoVivienda)))
            .andExpect(status().isCreated());

        // Validate the TipoVivienda in the database
        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoVivienda testTipoVivienda = tipoViviendaList.get(tipoViviendaList.size() - 1);
        assertThat(testTipoVivienda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoVivienda.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createTipoViviendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoViviendaRepository.findAll().size();

        // Create the TipoVivienda with an existing ID
        tipoVivienda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoViviendaMockMvc.perform(post("/api/tipo-viviendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoVivienda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoVivienda in the database
        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoViviendaRepository.findAll().size();
        // set the field null
        tipoVivienda.setNombre(null);

        // Create the TipoVivienda, which fails.

        restTipoViviendaMockMvc.perform(post("/api/tipo-viviendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoVivienda)))
            .andExpect(status().isBadRequest());

        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoViviendas() throws Exception {
        // Initialize the database
        tipoViviendaRepository.saveAndFlush(tipoVivienda);

        // Get all the tipoViviendaList
        restTipoViviendaMockMvc.perform(get("/api/tipo-viviendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoVivienda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoVivienda() throws Exception {
        // Initialize the database
        tipoViviendaRepository.saveAndFlush(tipoVivienda);

        // Get the tipoVivienda
        restTipoViviendaMockMvc.perform(get("/api/tipo-viviendas/{id}", tipoVivienda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoVivienda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoVivienda() throws Exception {
        // Get the tipoVivienda
        restTipoViviendaMockMvc.perform(get("/api/tipo-viviendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoVivienda() throws Exception {
        // Initialize the database
        tipoViviendaService.save(tipoVivienda);

        int databaseSizeBeforeUpdate = tipoViviendaRepository.findAll().size();

        // Update the tipoVivienda
        TipoVivienda updatedTipoVivienda = tipoViviendaRepository.findById(tipoVivienda.getId()).get();
        // Disconnect from session so that the updates on updatedTipoVivienda are not directly saved in db
        em.detach(updatedTipoVivienda);
        updatedTipoVivienda
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restTipoViviendaMockMvc.perform(put("/api/tipo-viviendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoVivienda)))
            .andExpect(status().isOk());

        // Validate the TipoVivienda in the database
        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeUpdate);
        TipoVivienda testTipoVivienda = tipoViviendaList.get(tipoViviendaList.size() - 1);
        assertThat(testTipoVivienda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoVivienda.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoVivienda() throws Exception {
        int databaseSizeBeforeUpdate = tipoViviendaRepository.findAll().size();

        // Create the TipoVivienda

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoViviendaMockMvc.perform(put("/api/tipo-viviendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoVivienda)))
            .andExpect(status().isBadRequest());

        // Validate the TipoVivienda in the database
        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoVivienda() throws Exception {
        // Initialize the database
        tipoViviendaService.save(tipoVivienda);

        int databaseSizeBeforeDelete = tipoViviendaRepository.findAll().size();

        // Delete the tipoVivienda
        restTipoViviendaMockMvc.perform(delete("/api/tipo-viviendas/{id}", tipoVivienda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoVivienda> tipoViviendaList = tipoViviendaRepository.findAll();
        assertThat(tipoViviendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
