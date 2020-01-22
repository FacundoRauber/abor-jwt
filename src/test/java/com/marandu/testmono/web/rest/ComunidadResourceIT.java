package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.Comunidad;
import com.marandu.testmono.repository.ComunidadRepository;
import com.marandu.testmono.service.ComunidadService;
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
 * Integration tests for the {@link ComunidadResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class ComunidadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private ComunidadService comunidadService;

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

    private MockMvc restComunidadMockMvc;

    private Comunidad comunidad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComunidadResource comunidadResource = new ComunidadResource(comunidadService);
        this.restComunidadMockMvc = MockMvcBuilders.standaloneSetup(comunidadResource)
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
    public static Comunidad createEntity(EntityManager em) {
        Comunidad comunidad = new Comunidad()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return comunidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comunidad createUpdatedEntity(EntityManager em) {
        Comunidad comunidad = new Comunidad()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return comunidad;
    }

    @BeforeEach
    public void initTest() {
        comunidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createComunidad() throws Exception {
        int databaseSizeBeforeCreate = comunidadRepository.findAll().size();

        // Create the Comunidad
        restComunidadMockMvc.perform(post("/api/comunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunidad)))
            .andExpect(status().isCreated());

        // Validate the Comunidad in the database
        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeCreate + 1);
        Comunidad testComunidad = comunidadList.get(comunidadList.size() - 1);
        assertThat(testComunidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComunidad.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createComunidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comunidadRepository.findAll().size();

        // Create the Comunidad with an existing ID
        comunidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComunidadMockMvc.perform(post("/api/comunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunidad)))
            .andExpect(status().isBadRequest());

        // Validate the Comunidad in the database
        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = comunidadRepository.findAll().size();
        // set the field null
        comunidad.setNombre(null);

        // Create the Comunidad, which fails.

        restComunidadMockMvc.perform(post("/api/comunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunidad)))
            .andExpect(status().isBadRequest());

        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComunidads() throws Exception {
        // Initialize the database
        comunidadRepository.saveAndFlush(comunidad);

        // Get all the comunidadList
        restComunidadMockMvc.perform(get("/api/comunidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comunidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getComunidad() throws Exception {
        // Initialize the database
        comunidadRepository.saveAndFlush(comunidad);

        // Get the comunidad
        restComunidadMockMvc.perform(get("/api/comunidads/{id}", comunidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comunidad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComunidad() throws Exception {
        // Get the comunidad
        restComunidadMockMvc.perform(get("/api/comunidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComunidad() throws Exception {
        // Initialize the database
        comunidadService.save(comunidad);

        int databaseSizeBeforeUpdate = comunidadRepository.findAll().size();

        // Update the comunidad
        Comunidad updatedComunidad = comunidadRepository.findById(comunidad.getId()).get();
        // Disconnect from session so that the updates on updatedComunidad are not directly saved in db
        em.detach(updatedComunidad);
        updatedComunidad
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restComunidadMockMvc.perform(put("/api/comunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComunidad)))
            .andExpect(status().isOk());

        // Validate the Comunidad in the database
        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeUpdate);
        Comunidad testComunidad = comunidadList.get(comunidadList.size() - 1);
        assertThat(testComunidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComunidad.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingComunidad() throws Exception {
        int databaseSizeBeforeUpdate = comunidadRepository.findAll().size();

        // Create the Comunidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComunidadMockMvc.perform(put("/api/comunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunidad)))
            .andExpect(status().isBadRequest());

        // Validate the Comunidad in the database
        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComunidad() throws Exception {
        // Initialize the database
        comunidadService.save(comunidad);

        int databaseSizeBeforeDelete = comunidadRepository.findAll().size();

        // Delete the comunidad
        restComunidadMockMvc.perform(delete("/api/comunidads/{id}", comunidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comunidad> comunidadList = comunidadRepository.findAll();
        assertThat(comunidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
