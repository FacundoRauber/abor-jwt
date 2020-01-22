package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.Relevamiento;
import com.marandu.testmono.repository.RelevamientoRepository;
import com.marandu.testmono.service.RelevamientoService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.marandu.testmono.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RelevamientoResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class RelevamientoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ESCUELA = false;
    private static final Boolean UPDATED_ESCUELA = true;

    private static final Boolean DEFAULT_PUESTO_SALUD = false;
    private static final Boolean UPDATED_PUESTO_SALUD = true;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private RelevamientoRepository relevamientoRepository;

    @Autowired
    private RelevamientoService relevamientoService;

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

    private MockMvc restRelevamientoMockMvc;

    private Relevamiento relevamiento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelevamientoResource relevamientoResource = new RelevamientoResource(relevamientoService);
        this.restRelevamientoMockMvc = MockMvcBuilders.standaloneSetup(relevamientoResource)
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
    public static Relevamiento createEntity(EntityManager em) {
        Relevamiento relevamiento = new Relevamiento()
            .fecha(DEFAULT_FECHA)
            .escuela(DEFAULT_ESCUELA)
            .puestoSalud(DEFAULT_PUESTO_SALUD)
            .estado(DEFAULT_ESTADO);
        return relevamiento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relevamiento createUpdatedEntity(EntityManager em) {
        Relevamiento relevamiento = new Relevamiento()
            .fecha(UPDATED_FECHA)
            .escuela(UPDATED_ESCUELA)
            .puestoSalud(UPDATED_PUESTO_SALUD)
            .estado(UPDATED_ESTADO);
        return relevamiento;
    }

    @BeforeEach
    public void initTest() {
        relevamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelevamiento() throws Exception {
        int databaseSizeBeforeCreate = relevamientoRepository.findAll().size();

        // Create the Relevamiento
        restRelevamientoMockMvc.perform(post("/api/relevamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relevamiento)))
            .andExpect(status().isCreated());

        // Validate the Relevamiento in the database
        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Relevamiento testRelevamiento = relevamientoList.get(relevamientoList.size() - 1);
        assertThat(testRelevamiento.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRelevamiento.isEscuela()).isEqualTo(DEFAULT_ESCUELA);
        assertThat(testRelevamiento.isPuestoSalud()).isEqualTo(DEFAULT_PUESTO_SALUD);
        assertThat(testRelevamiento.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createRelevamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relevamientoRepository.findAll().size();

        // Create the Relevamiento with an existing ID
        relevamiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelevamientoMockMvc.perform(post("/api/relevamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relevamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Relevamiento in the database
        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = relevamientoRepository.findAll().size();
        // set the field null
        relevamiento.setFecha(null);

        // Create the Relevamiento, which fails.

        restRelevamientoMockMvc.perform(post("/api/relevamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relevamiento)))
            .andExpect(status().isBadRequest());

        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelevamientos() throws Exception {
        // Initialize the database
        relevamientoRepository.saveAndFlush(relevamiento);

        // Get all the relevamientoList
        restRelevamientoMockMvc.perform(get("/api/relevamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relevamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].escuela").value(hasItem(DEFAULT_ESCUELA.booleanValue())))
            .andExpect(jsonPath("$.[*].puestoSalud").value(hasItem(DEFAULT_PUESTO_SALUD.booleanValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRelevamiento() throws Exception {
        // Initialize the database
        relevamientoRepository.saveAndFlush(relevamiento);

        // Get the relevamiento
        restRelevamientoMockMvc.perform(get("/api/relevamientos/{id}", relevamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relevamiento.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.escuela").value(DEFAULT_ESCUELA.booleanValue()))
            .andExpect(jsonPath("$.puestoSalud").value(DEFAULT_PUESTO_SALUD.booleanValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRelevamiento() throws Exception {
        // Get the relevamiento
        restRelevamientoMockMvc.perform(get("/api/relevamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelevamiento() throws Exception {
        // Initialize the database
        relevamientoService.save(relevamiento);

        int databaseSizeBeforeUpdate = relevamientoRepository.findAll().size();

        // Update the relevamiento
        Relevamiento updatedRelevamiento = relevamientoRepository.findById(relevamiento.getId()).get();
        // Disconnect from session so that the updates on updatedRelevamiento are not directly saved in db
        em.detach(updatedRelevamiento);
        updatedRelevamiento
            .fecha(UPDATED_FECHA)
            .escuela(UPDATED_ESCUELA)
            .puestoSalud(UPDATED_PUESTO_SALUD)
            .estado(UPDATED_ESTADO);

        restRelevamientoMockMvc.perform(put("/api/relevamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelevamiento)))
            .andExpect(status().isOk());

        // Validate the Relevamiento in the database
        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeUpdate);
        Relevamiento testRelevamiento = relevamientoList.get(relevamientoList.size() - 1);
        assertThat(testRelevamiento.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRelevamiento.isEscuela()).isEqualTo(UPDATED_ESCUELA);
        assertThat(testRelevamiento.isPuestoSalud()).isEqualTo(UPDATED_PUESTO_SALUD);
        assertThat(testRelevamiento.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingRelevamiento() throws Exception {
        int databaseSizeBeforeUpdate = relevamientoRepository.findAll().size();

        // Create the Relevamiento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelevamientoMockMvc.perform(put("/api/relevamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relevamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Relevamiento in the database
        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelevamiento() throws Exception {
        // Initialize the database
        relevamientoService.save(relevamiento);

        int databaseSizeBeforeDelete = relevamientoRepository.findAll().size();

        // Delete the relevamiento
        restRelevamientoMockMvc.perform(delete("/api/relevamientos/{id}", relevamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relevamiento> relevamientoList = relevamientoRepository.findAll();
        assertThat(relevamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
