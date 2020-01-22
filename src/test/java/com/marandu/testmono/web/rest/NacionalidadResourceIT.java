package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.Nacionalidad;
import com.marandu.testmono.repository.NacionalidadRepository;
import com.marandu.testmono.service.NacionalidadService;
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
 * Integration tests for the {@link NacionalidadResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class NacionalidadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Autowired
    private NacionalidadService nacionalidadService;

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

    private MockMvc restNacionalidadMockMvc;

    private Nacionalidad nacionalidad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NacionalidadResource nacionalidadResource = new NacionalidadResource(nacionalidadService);
        this.restNacionalidadMockMvc = MockMvcBuilders.standaloneSetup(nacionalidadResource)
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
    public static Nacionalidad createEntity(EntityManager em) {
        Nacionalidad nacionalidad = new Nacionalidad()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return nacionalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nacionalidad createUpdatedEntity(EntityManager em) {
        Nacionalidad nacionalidad = new Nacionalidad()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return nacionalidad;
    }

    @BeforeEach
    public void initTest() {
        nacionalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createNacionalidad() throws Exception {
        int databaseSizeBeforeCreate = nacionalidadRepository.findAll().size();

        // Create the Nacionalidad
        restNacionalidadMockMvc.perform(post("/api/nacionalidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidad)))
            .andExpect(status().isCreated());

        // Validate the Nacionalidad in the database
        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeCreate + 1);
        Nacionalidad testNacionalidad = nacionalidadList.get(nacionalidadList.size() - 1);
        assertThat(testNacionalidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testNacionalidad.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createNacionalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nacionalidadRepository.findAll().size();

        // Create the Nacionalidad with an existing ID
        nacionalidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNacionalidadMockMvc.perform(post("/api/nacionalidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidad)))
            .andExpect(status().isBadRequest());

        // Validate the Nacionalidad in the database
        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = nacionalidadRepository.findAll().size();
        // set the field null
        nacionalidad.setNombre(null);

        // Create the Nacionalidad, which fails.

        restNacionalidadMockMvc.perform(post("/api/nacionalidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidad)))
            .andExpect(status().isBadRequest());

        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNacionalidads() throws Exception {
        // Initialize the database
        nacionalidadRepository.saveAndFlush(nacionalidad);

        // Get all the nacionalidadList
        restNacionalidadMockMvc.perform(get("/api/nacionalidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nacionalidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNacionalidad() throws Exception {
        // Initialize the database
        nacionalidadRepository.saveAndFlush(nacionalidad);

        // Get the nacionalidad
        restNacionalidadMockMvc.perform(get("/api/nacionalidads/{id}", nacionalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nacionalidad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNacionalidad() throws Exception {
        // Get the nacionalidad
        restNacionalidadMockMvc.perform(get("/api/nacionalidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNacionalidad() throws Exception {
        // Initialize the database
        nacionalidadService.save(nacionalidad);

        int databaseSizeBeforeUpdate = nacionalidadRepository.findAll().size();

        // Update the nacionalidad
        Nacionalidad updatedNacionalidad = nacionalidadRepository.findById(nacionalidad.getId()).get();
        // Disconnect from session so that the updates on updatedNacionalidad are not directly saved in db
        em.detach(updatedNacionalidad);
        updatedNacionalidad
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restNacionalidadMockMvc.perform(put("/api/nacionalidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNacionalidad)))
            .andExpect(status().isOk());

        // Validate the Nacionalidad in the database
        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeUpdate);
        Nacionalidad testNacionalidad = nacionalidadList.get(nacionalidadList.size() - 1);
        assertThat(testNacionalidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNacionalidad.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingNacionalidad() throws Exception {
        int databaseSizeBeforeUpdate = nacionalidadRepository.findAll().size();

        // Create the Nacionalidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNacionalidadMockMvc.perform(put("/api/nacionalidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nacionalidad)))
            .andExpect(status().isBadRequest());

        // Validate the Nacionalidad in the database
        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNacionalidad() throws Exception {
        // Initialize the database
        nacionalidadService.save(nacionalidad);

        int databaseSizeBeforeDelete = nacionalidadRepository.findAll().size();

        // Delete the nacionalidad
        restNacionalidadMockMvc.perform(delete("/api/nacionalidads/{id}", nacionalidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nacionalidad> nacionalidadList = nacionalidadRepository.findAll();
        assertThat(nacionalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
