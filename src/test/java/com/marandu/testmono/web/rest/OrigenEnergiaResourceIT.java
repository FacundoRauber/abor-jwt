package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.OrigenEnergia;
import com.marandu.testmono.repository.OrigenEnergiaRepository;
import com.marandu.testmono.service.OrigenEnergiaService;
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
 * Integration tests for the {@link OrigenEnergiaResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class OrigenEnergiaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private OrigenEnergiaRepository origenEnergiaRepository;

    @Autowired
    private OrigenEnergiaService origenEnergiaService;

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

    private MockMvc restOrigenEnergiaMockMvc;

    private OrigenEnergia origenEnergia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrigenEnergiaResource origenEnergiaResource = new OrigenEnergiaResource(origenEnergiaService);
        this.restOrigenEnergiaMockMvc = MockMvcBuilders.standaloneSetup(origenEnergiaResource)
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
    public static OrigenEnergia createEntity(EntityManager em) {
        OrigenEnergia origenEnergia = new OrigenEnergia()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return origenEnergia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrigenEnergia createUpdatedEntity(EntityManager em) {
        OrigenEnergia origenEnergia = new OrigenEnergia()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return origenEnergia;
    }

    @BeforeEach
    public void initTest() {
        origenEnergia = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrigenEnergia() throws Exception {
        int databaseSizeBeforeCreate = origenEnergiaRepository.findAll().size();

        // Create the OrigenEnergia
        restOrigenEnergiaMockMvc.perform(post("/api/origen-energias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenEnergia)))
            .andExpect(status().isCreated());

        // Validate the OrigenEnergia in the database
        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeCreate + 1);
        OrigenEnergia testOrigenEnergia = origenEnergiaList.get(origenEnergiaList.size() - 1);
        assertThat(testOrigenEnergia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOrigenEnergia.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createOrigenEnergiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = origenEnergiaRepository.findAll().size();

        // Create the OrigenEnergia with an existing ID
        origenEnergia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrigenEnergiaMockMvc.perform(post("/api/origen-energias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenEnergia)))
            .andExpect(status().isBadRequest());

        // Validate the OrigenEnergia in the database
        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = origenEnergiaRepository.findAll().size();
        // set the field null
        origenEnergia.setNombre(null);

        // Create the OrigenEnergia, which fails.

        restOrigenEnergiaMockMvc.perform(post("/api/origen-energias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenEnergia)))
            .andExpect(status().isBadRequest());

        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrigenEnergias() throws Exception {
        // Initialize the database
        origenEnergiaRepository.saveAndFlush(origenEnergia);

        // Get all the origenEnergiaList
        restOrigenEnergiaMockMvc.perform(get("/api/origen-energias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(origenEnergia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrigenEnergia() throws Exception {
        // Initialize the database
        origenEnergiaRepository.saveAndFlush(origenEnergia);

        // Get the origenEnergia
        restOrigenEnergiaMockMvc.perform(get("/api/origen-energias/{id}", origenEnergia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(origenEnergia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrigenEnergia() throws Exception {
        // Get the origenEnergia
        restOrigenEnergiaMockMvc.perform(get("/api/origen-energias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrigenEnergia() throws Exception {
        // Initialize the database
        origenEnergiaService.save(origenEnergia);

        int databaseSizeBeforeUpdate = origenEnergiaRepository.findAll().size();

        // Update the origenEnergia
        OrigenEnergia updatedOrigenEnergia = origenEnergiaRepository.findById(origenEnergia.getId()).get();
        // Disconnect from session so that the updates on updatedOrigenEnergia are not directly saved in db
        em.detach(updatedOrigenEnergia);
        updatedOrigenEnergia
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restOrigenEnergiaMockMvc.perform(put("/api/origen-energias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrigenEnergia)))
            .andExpect(status().isOk());

        // Validate the OrigenEnergia in the database
        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeUpdate);
        OrigenEnergia testOrigenEnergia = origenEnergiaList.get(origenEnergiaList.size() - 1);
        assertThat(testOrigenEnergia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOrigenEnergia.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrigenEnergia() throws Exception {
        int databaseSizeBeforeUpdate = origenEnergiaRepository.findAll().size();

        // Create the OrigenEnergia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrigenEnergiaMockMvc.perform(put("/api/origen-energias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(origenEnergia)))
            .andExpect(status().isBadRequest());

        // Validate the OrigenEnergia in the database
        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrigenEnergia() throws Exception {
        // Initialize the database
        origenEnergiaService.save(origenEnergia);

        int databaseSizeBeforeDelete = origenEnergiaRepository.findAll().size();

        // Delete the origenEnergia
        restOrigenEnergiaMockMvc.perform(delete("/api/origen-energias/{id}", origenEnergia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrigenEnergia> origenEnergiaList = origenEnergiaRepository.findAll();
        assertThat(origenEnergiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
