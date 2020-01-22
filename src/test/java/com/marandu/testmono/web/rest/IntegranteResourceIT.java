package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.Integrante;
import com.marandu.testmono.repository.IntegranteRepository;
import com.marandu.testmono.service.IntegranteService;
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

import com.marandu.testmono.domain.enumeration.Sexo;
/**
 * Integration tests for the {@link IntegranteResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class IntegranteResourceIT {

    private static final Integer DEFAULT_DNI = 1;
    private static final Integer UPDATED_DNI = 2;

    private static final String DEFAULT_APELLLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EDAD = 0;
    private static final Integer UPDATED_EDAD = 1;

    private static final Sexo DEFAULT_SEXO = Sexo.Hombre;
    private static final Sexo UPDATED_SEXO = Sexo.Mujer;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private IntegranteRepository integranteRepository;

    @Autowired
    private IntegranteService integranteService;

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

    private MockMvc restIntegranteMockMvc;

    private Integrante integrante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntegranteResource integranteResource = new IntegranteResource(integranteService);
        this.restIntegranteMockMvc = MockMvcBuilders.standaloneSetup(integranteResource)
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
    public static Integrante createEntity(EntityManager em) {
        Integrante integrante = new Integrante()
            .dni(DEFAULT_DNI)
            .apelllido(DEFAULT_APELLLIDO)
            .nombre(DEFAULT_NOMBRE)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .edad(DEFAULT_EDAD)
            .sexo(DEFAULT_SEXO)
            .estado(DEFAULT_ESTADO);
        return integrante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Integrante createUpdatedEntity(EntityManager em) {
        Integrante integrante = new Integrante()
            .dni(UPDATED_DNI)
            .apelllido(UPDATED_APELLLIDO)
            .nombre(UPDATED_NOMBRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edad(UPDATED_EDAD)
            .sexo(UPDATED_SEXO)
            .estado(UPDATED_ESTADO);
        return integrante;
    }

    @BeforeEach
    public void initTest() {
        integrante = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntegrante() throws Exception {
        int databaseSizeBeforeCreate = integranteRepository.findAll().size();

        // Create the Integrante
        restIntegranteMockMvc.perform(post("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isCreated());

        // Validate the Integrante in the database
        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeCreate + 1);
        Integrante testIntegrante = integranteList.get(integranteList.size() - 1);
        assertThat(testIntegrante.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testIntegrante.getApelllido()).isEqualTo(DEFAULT_APELLLIDO);
        assertThat(testIntegrante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIntegrante.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testIntegrante.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testIntegrante.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testIntegrante.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createIntegranteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = integranteRepository.findAll().size();

        // Create the Integrante with an existing ID
        integrante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegranteMockMvc.perform(post("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isBadRequest());

        // Validate the Integrante in the database
        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = integranteRepository.findAll().size();
        // set the field null
        integrante.setDni(null);

        // Create the Integrante, which fails.

        restIntegranteMockMvc.perform(post("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isBadRequest());

        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApelllidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = integranteRepository.findAll().size();
        // set the field null
        integrante.setApelllido(null);

        // Create the Integrante, which fails.

        restIntegranteMockMvc.perform(post("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isBadRequest());

        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = integranteRepository.findAll().size();
        // set the field null
        integrante.setNombre(null);

        // Create the Integrante, which fails.

        restIntegranteMockMvc.perform(post("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isBadRequest());

        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntegrantes() throws Exception {
        // Initialize the database
        integranteRepository.saveAndFlush(integrante);

        // Get all the integranteList
        restIntegranteMockMvc.perform(get("/api/integrantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrante.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].apelllido").value(hasItem(DEFAULT_APELLLIDO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIntegrante() throws Exception {
        // Initialize the database
        integranteRepository.saveAndFlush(integrante);

        // Get the integrante
        restIntegranteMockMvc.perform(get("/api/integrantes/{id}", integrante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(integrante.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.apelllido").value(DEFAULT_APELLLIDO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIntegrante() throws Exception {
        // Get the integrante
        restIntegranteMockMvc.perform(get("/api/integrantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrante() throws Exception {
        // Initialize the database
        integranteService.save(integrante);

        int databaseSizeBeforeUpdate = integranteRepository.findAll().size();

        // Update the integrante
        Integrante updatedIntegrante = integranteRepository.findById(integrante.getId()).get();
        // Disconnect from session so that the updates on updatedIntegrante are not directly saved in db
        em.detach(updatedIntegrante);
        updatedIntegrante
            .dni(UPDATED_DNI)
            .apelllido(UPDATED_APELLLIDO)
            .nombre(UPDATED_NOMBRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .edad(UPDATED_EDAD)
            .sexo(UPDATED_SEXO)
            .estado(UPDATED_ESTADO);

        restIntegranteMockMvc.perform(put("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntegrante)))
            .andExpect(status().isOk());

        // Validate the Integrante in the database
        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeUpdate);
        Integrante testIntegrante = integranteList.get(integranteList.size() - 1);
        assertThat(testIntegrante.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testIntegrante.getApelllido()).isEqualTo(UPDATED_APELLLIDO);
        assertThat(testIntegrante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIntegrante.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testIntegrante.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testIntegrante.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testIntegrante.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingIntegrante() throws Exception {
        int databaseSizeBeforeUpdate = integranteRepository.findAll().size();

        // Create the Integrante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegranteMockMvc.perform(put("/api/integrantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrante)))
            .andExpect(status().isBadRequest());

        // Validate the Integrante in the database
        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntegrante() throws Exception {
        // Initialize the database
        integranteService.save(integrante);

        int databaseSizeBeforeDelete = integranteRepository.findAll().size();

        // Delete the integrante
        restIntegranteMockMvc.perform(delete("/api/integrantes/{id}", integrante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Integrante> integranteList = integranteRepository.findAll();
        assertThat(integranteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
