package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.OrigenAgua;
import com.marandu.testmono.repository.OrigenAguaRepository;
import com.marandu.testmono.service.OrigenAguaService;
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
 * Integration tests for the {@link OrigenAguaResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class OrigenAguaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private OrigenAguaRepository origenAguaRepository;

    @Autowired
    private OrigenAguaService origenAguaService;

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

    private MockMvc restOrigenAguaMockMvc;

    private OrigenAgua origenAgua;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrigenAguaResource origenAguaResource = new OrigenAguaResource(origenAguaService);
        this.restOrigenAguaMockMvc = MockMvcBuilders.standaloneSetup(origenAguaResource)
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
    public static OrigenAgua createEntity(EntityManager em) {
        OrigenAgua origenAgua = new OrigenAgua()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return origenAgua;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigenAgua createUpdatedEntity(EntityManager em) {
        OrigenAgua origenAgua = new OrigenAgua()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return origenAgua;
    }

    @BeforeEach
    public void initTest() {
        origenAgua = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigenAgua() throws Exception {
        int databaseSizeBeforeCreate = origenAguaRepository.findAll().size();

        // Create the OrigenAgua
        restOrigenAguaMockMvc.perform(post("/api/origen-aguas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenAgua)))
            .andExpect(status().isCreated());

        // Validate the OrigenAgua in the database
        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeCreate + 1);
        OrigenAgua testOrigenAgua = origenAguaList.get(origenAguaList.size() - 1);
        assertThat(testOrigenAgua.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOrigenAgua.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createOrigenAguaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = origenAguaRepository.findAll().size();

        // Create the OrigenAgua with an existing ID
        origenAgua.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigenAguaMockMvc.perform(post("/api/origen-aguas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenAgua)))
            .andExpect(status().isBadRequest());

        // Validate the OrigenAgua in the database
        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = origenAguaRepository.findAll().size();
        // set the field null
        origenAgua.setNombre(null);

        // Create the OrigenAgua, which fails.

        restOrigenAguaMockMvc.perform(post("/api/origen-aguas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenAgua)))
            .andExpect(status().isBadRequest());

        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrigenAguas() throws Exception {
        // Initialize the database
        origenAguaRepository.saveAndFlush(origenAgua);

        // Get all the origenAguaList
        restOrigenAguaMockMvc.perform(get("/api/origen-aguas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origenAgua.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrigenAgua() throws Exception {
        // Initialize the database
        origenAguaRepository.saveAndFlush(origenAgua);

        // Get the origenAgua
        restOrigenAguaMockMvc.perform(get("/api/origen-aguas/{id}", origenAgua.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(origenAgua.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrigenAgua() throws Exception {
        // Get the origenAgua
        restOrigenAguaMockMvc.perform(get("/api/origen-aguas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigenAgua() throws Exception {
        // Initialize the database
        origenAguaService.save(origenAgua);

        int databaseSizeBeforeUpdate = origenAguaRepository.findAll().size();

        // Update the origenAgua
        OrigenAgua updatedOrigenAgua = origenAguaRepository.findById(origenAgua.getId()).get();
        // Disconnect from session so that the updates on updatedOrigenAgua are not directly saved in db
        em.detach(updatedOrigenAgua);
        updatedOrigenAgua
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restOrigenAguaMockMvc.perform(put("/api/origen-aguas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrigenAgua)))
            .andExpect(status().isOk());

        // Validate the OrigenAgua in the database
        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeUpdate);
        OrigenAgua testOrigenAgua = origenAguaList.get(origenAguaList.size() - 1);
        assertThat(testOrigenAgua.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOrigenAgua.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigenAgua() throws Exception {
        int databaseSizeBeforeUpdate = origenAguaRepository.findAll().size();

        // Create the OrigenAgua

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigenAguaMockMvc.perform(put("/api/origen-aguas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenAgua)))
            .andExpect(status().isBadRequest());

        // Validate the OrigenAgua in the database
        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrigenAgua() throws Exception {
        // Initialize the database
        origenAguaService.save(origenAgua);

        int databaseSizeBeforeDelete = origenAguaRepository.findAll().size();

        // Delete the origenAgua
        restOrigenAguaMockMvc.perform(delete("/api/origen-aguas/{id}", origenAgua.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigenAgua> origenAguaList = origenAguaRepository.findAll();
        assertThat(origenAguaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
