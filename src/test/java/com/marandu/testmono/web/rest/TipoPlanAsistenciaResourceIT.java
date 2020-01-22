package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.TipoPlanAsistencia;
import com.marandu.testmono.repository.TipoPlanAsistenciaRepository;
import com.marandu.testmono.service.TipoPlanAsistenciaService;
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
 * Integration tests for the {@link TipoPlanAsistenciaResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class TipoPlanAsistenciaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private TipoPlanAsistenciaRepository tipoPlanAsistenciaRepository;

    @Autowired
    private TipoPlanAsistenciaService tipoPlanAsistenciaService;

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

    private MockMvc restTipoPlanAsistenciaMockMvc;

    private TipoPlanAsistencia tipoPlanAsistencia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoPlanAsistenciaResource tipoPlanAsistenciaResource = new TipoPlanAsistenciaResource(tipoPlanAsistenciaService);
        this.restTipoPlanAsistenciaMockMvc = MockMvcBuilders.standaloneSetup(tipoPlanAsistenciaResource)
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
    public static TipoPlanAsistencia createEntity(EntityManager em) {
        TipoPlanAsistencia tipoPlanAsistencia = new TipoPlanAsistencia()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return tipoPlanAsistencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPlanAsistencia createUpdatedEntity(EntityManager em) {
        TipoPlanAsistencia tipoPlanAsistencia = new TipoPlanAsistencia()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return tipoPlanAsistencia;
    }

    @BeforeEach
    public void initTest() {
        tipoPlanAsistencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPlanAsistencia() throws Exception {
        int databaseSizeBeforeCreate = tipoPlanAsistenciaRepository.findAll().size();

        // Create the TipoPlanAsistencia
        restTipoPlanAsistenciaMockMvc.perform(post("/api/tipo-plan-asistencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlanAsistencia)))
            .andExpect(status().isCreated());

        // Validate the TipoPlanAsistencia in the database
        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPlanAsistencia testTipoPlanAsistencia = tipoPlanAsistenciaList.get(tipoPlanAsistenciaList.size() - 1);
        assertThat(testTipoPlanAsistencia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoPlanAsistencia.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createTipoPlanAsistenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPlanAsistenciaRepository.findAll().size();

        // Create the TipoPlanAsistencia with an existing ID
        tipoPlanAsistencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPlanAsistenciaMockMvc.perform(post("/api/tipo-plan-asistencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlanAsistencia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPlanAsistencia in the database
        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPlanAsistenciaRepository.findAll().size();
        // set the field null
        tipoPlanAsistencia.setNombre(null);

        // Create the TipoPlanAsistencia, which fails.

        restTipoPlanAsistenciaMockMvc.perform(post("/api/tipo-plan-asistencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlanAsistencia)))
            .andExpect(status().isBadRequest());

        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoPlanAsistencias() throws Exception {
        // Initialize the database
        tipoPlanAsistenciaRepository.saveAndFlush(tipoPlanAsistencia);

        // Get all the tipoPlanAsistenciaList
        restTipoPlanAsistenciaMockMvc.perform(get("/api/tipo-plan-asistencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPlanAsistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoPlanAsistencia() throws Exception {
        // Initialize the database
        tipoPlanAsistenciaRepository.saveAndFlush(tipoPlanAsistencia);

        // Get the tipoPlanAsistencia
        restTipoPlanAsistenciaMockMvc.perform(get("/api/tipo-plan-asistencias/{id}", tipoPlanAsistencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPlanAsistencia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoPlanAsistencia() throws Exception {
        // Get the tipoPlanAsistencia
        restTipoPlanAsistenciaMockMvc.perform(get("/api/tipo-plan-asistencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPlanAsistencia() throws Exception {
        // Initialize the database
        tipoPlanAsistenciaService.save(tipoPlanAsistencia);

        int databaseSizeBeforeUpdate = tipoPlanAsistenciaRepository.findAll().size();

        // Update the tipoPlanAsistencia
        TipoPlanAsistencia updatedTipoPlanAsistencia = tipoPlanAsistenciaRepository.findById(tipoPlanAsistencia.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPlanAsistencia are not directly saved in db
        em.detach(updatedTipoPlanAsistencia);
        updatedTipoPlanAsistencia
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restTipoPlanAsistenciaMockMvc.perform(put("/api/tipo-plan-asistencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoPlanAsistencia)))
            .andExpect(status().isOk());

        // Validate the TipoPlanAsistencia in the database
        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeUpdate);
        TipoPlanAsistencia testTipoPlanAsistencia = tipoPlanAsistenciaList.get(tipoPlanAsistenciaList.size() - 1);
        assertThat(testTipoPlanAsistencia.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoPlanAsistencia.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPlanAsistencia() throws Exception {
        int databaseSizeBeforeUpdate = tipoPlanAsistenciaRepository.findAll().size();

        // Create the TipoPlanAsistencia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPlanAsistenciaMockMvc.perform(put("/api/tipo-plan-asistencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPlanAsistencia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPlanAsistencia in the database
        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoPlanAsistencia() throws Exception {
        // Initialize the database
        tipoPlanAsistenciaService.save(tipoPlanAsistencia);

        int databaseSizeBeforeDelete = tipoPlanAsistenciaRepository.findAll().size();

        // Delete the tipoPlanAsistencia
        restTipoPlanAsistenciaMockMvc.perform(delete("/api/tipo-plan-asistencias/{id}", tipoPlanAsistencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPlanAsistencia> tipoPlanAsistenciaList = tipoPlanAsistenciaRepository.findAll();
        assertThat(tipoPlanAsistenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
