package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.NivelEducativo;
import com.marandu.testmono.repository.NivelEducativoRepository;
import com.marandu.testmono.service.NivelEducativoService;
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
 * Integration tests for the {@link NivelEducativoResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class NivelEducativoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private NivelEducativoRepository nivelEducativoRepository;

    @Autowired
    private NivelEducativoService nivelEducativoService;

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

    private MockMvc restNivelEducativoMockMvc;

    private NivelEducativo nivelEducativo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NivelEducativoResource nivelEducativoResource = new NivelEducativoResource(nivelEducativoService);
        this.restNivelEducativoMockMvc = MockMvcBuilders.standaloneSetup(nivelEducativoResource)
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
    public static NivelEducativo createEntity(EntityManager em) {
        NivelEducativo nivelEducativo = new NivelEducativo()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return nivelEducativo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelEducativo createUpdatedEntity(EntityManager em) {
        NivelEducativo nivelEducativo = new NivelEducativo()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return nivelEducativo;
    }

    @BeforeEach
    public void initTest() {
        nivelEducativo = createEntity(em);
    }

    @Test
    @Transactional
    public void createNivelEducativo() throws Exception {
        int databaseSizeBeforeCreate = nivelEducativoRepository.findAll().size();

        // Create the NivelEducativo
        restNivelEducativoMockMvc.perform(post("/api/nivel-educativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelEducativo)))
            .andExpect(status().isCreated());

        // Validate the NivelEducativo in the database
        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeCreate + 1);
        NivelEducativo testNivelEducativo = nivelEducativoList.get(nivelEducativoList.size() - 1);
        assertThat(testNivelEducativo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testNivelEducativo.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createNivelEducativoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nivelEducativoRepository.findAll().size();

        // Create the NivelEducativo with an existing ID
        nivelEducativo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelEducativoMockMvc.perform(post("/api/nivel-educativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelEducativo)))
            .andExpect(status().isBadRequest());

        // Validate the NivelEducativo in the database
        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelEducativoRepository.findAll().size();
        // set the field null
        nivelEducativo.setNombre(null);

        // Create the NivelEducativo, which fails.

        restNivelEducativoMockMvc.perform(post("/api/nivel-educativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelEducativo)))
            .andExpect(status().isBadRequest());

        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNivelEducativos() throws Exception {
        // Initialize the database
        nivelEducativoRepository.saveAndFlush(nivelEducativo);

        // Get all the nivelEducativoList
        restNivelEducativoMockMvc.perform(get("/api/nivel-educativos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelEducativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNivelEducativo() throws Exception {
        // Initialize the database
        nivelEducativoRepository.saveAndFlush(nivelEducativo);

        // Get the nivelEducativo
        restNivelEducativoMockMvc.perform(get("/api/nivel-educativos/{id}", nivelEducativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nivelEducativo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNivelEducativo() throws Exception {
        // Get the nivelEducativo
        restNivelEducativoMockMvc.perform(get("/api/nivel-educativos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNivelEducativo() throws Exception {
        // Initialize the database
        nivelEducativoService.save(nivelEducativo);

        int databaseSizeBeforeUpdate = nivelEducativoRepository.findAll().size();

        // Update the nivelEducativo
        NivelEducativo updatedNivelEducativo = nivelEducativoRepository.findById(nivelEducativo.getId()).get();
        // Disconnect from session so that the updates on updatedNivelEducativo are not directly saved in db
        em.detach(updatedNivelEducativo);
        updatedNivelEducativo
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restNivelEducativoMockMvc.perform(put("/api/nivel-educativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNivelEducativo)))
            .andExpect(status().isOk());

        // Validate the NivelEducativo in the database
        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeUpdate);
        NivelEducativo testNivelEducativo = nivelEducativoList.get(nivelEducativoList.size() - 1);
        assertThat(testNivelEducativo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNivelEducativo.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingNivelEducativo() throws Exception {
        int databaseSizeBeforeUpdate = nivelEducativoRepository.findAll().size();

        // Create the NivelEducativo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelEducativoMockMvc.perform(put("/api/nivel-educativos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nivelEducativo)))
            .andExpect(status().isBadRequest());

        // Validate the NivelEducativo in the database
        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNivelEducativo() throws Exception {
        // Initialize the database
        nivelEducativoService.save(nivelEducativo);

        int databaseSizeBeforeDelete = nivelEducativoRepository.findAll().size();

        // Delete the nivelEducativo
        restNivelEducativoMockMvc.perform(delete("/api/nivel-educativos/{id}", nivelEducativo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NivelEducativo> nivelEducativoList = nivelEducativoRepository.findAll();
        assertThat(nivelEducativoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
