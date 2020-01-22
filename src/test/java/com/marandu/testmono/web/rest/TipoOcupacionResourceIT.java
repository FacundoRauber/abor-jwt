package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.TipoOcupacion;
import com.marandu.testmono.repository.TipoOcupacionRepository;
import com.marandu.testmono.service.TipoOcupacionService;
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
 * Integration tests for the {@link TipoOcupacionResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class TipoOcupacionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private TipoOcupacionRepository tipoOcupacionRepository;

    @Autowired
    private TipoOcupacionService tipoOcupacionService;

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

    private MockMvc restTipoOcupacionMockMvc;

    private TipoOcupacion tipoOcupacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoOcupacionResource tipoOcupacionResource = new TipoOcupacionResource(tipoOcupacionService);
        this.restTipoOcupacionMockMvc = MockMvcBuilders.standaloneSetup(tipoOcupacionResource)
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
    public static TipoOcupacion createEntity(EntityManager em) {
        TipoOcupacion tipoOcupacion = new TipoOcupacion()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return tipoOcupacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoOcupacion createUpdatedEntity(EntityManager em) {
        TipoOcupacion tipoOcupacion = new TipoOcupacion()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return tipoOcupacion;
    }

    @BeforeEach
    public void initTest() {
        tipoOcupacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoOcupacion() throws Exception {
        int databaseSizeBeforeCreate = tipoOcupacionRepository.findAll().size();

        // Create the TipoOcupacion
        restTipoOcupacionMockMvc.perform(post("/api/tipo-ocupacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOcupacion)))
            .andExpect(status().isCreated());

        // Validate the TipoOcupacion in the database
        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeCreate + 1);
        TipoOcupacion testTipoOcupacion = tipoOcupacionList.get(tipoOcupacionList.size() - 1);
        assertThat(testTipoOcupacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoOcupacion.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createTipoOcupacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoOcupacionRepository.findAll().size();

        // Create the TipoOcupacion with an existing ID
        tipoOcupacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoOcupacionMockMvc.perform(post("/api/tipo-ocupacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOcupacion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoOcupacion in the database
        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoOcupacionRepository.findAll().size();
        // set the field null
        tipoOcupacion.setNombre(null);

        // Create the TipoOcupacion, which fails.

        restTipoOcupacionMockMvc.perform(post("/api/tipo-ocupacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOcupacion)))
            .andExpect(status().isBadRequest());

        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoOcupacions() throws Exception {
        // Initialize the database
        tipoOcupacionRepository.saveAndFlush(tipoOcupacion);

        // Get all the tipoOcupacionList
        restTipoOcupacionMockMvc.perform(get("/api/tipo-ocupacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoOcupacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTipoOcupacion() throws Exception {
        // Initialize the database
        tipoOcupacionRepository.saveAndFlush(tipoOcupacion);

        // Get the tipoOcupacion
        restTipoOcupacionMockMvc.perform(get("/api/tipo-ocupacions/{id}", tipoOcupacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoOcupacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoOcupacion() throws Exception {
        // Get the tipoOcupacion
        restTipoOcupacionMockMvc.perform(get("/api/tipo-ocupacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoOcupacion() throws Exception {
        // Initialize the database
        tipoOcupacionService.save(tipoOcupacion);

        int databaseSizeBeforeUpdate = tipoOcupacionRepository.findAll().size();

        // Update the tipoOcupacion
        TipoOcupacion updatedTipoOcupacion = tipoOcupacionRepository.findById(tipoOcupacion.getId()).get();
        // Disconnect from session so that the updates on updatedTipoOcupacion are not directly saved in db
        em.detach(updatedTipoOcupacion);
        updatedTipoOcupacion
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restTipoOcupacionMockMvc.perform(put("/api/tipo-ocupacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoOcupacion)))
            .andExpect(status().isOk());

        // Validate the TipoOcupacion in the database
        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeUpdate);
        TipoOcupacion testTipoOcupacion = tipoOcupacionList.get(tipoOcupacionList.size() - 1);
        assertThat(testTipoOcupacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoOcupacion.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoOcupacion() throws Exception {
        int databaseSizeBeforeUpdate = tipoOcupacionRepository.findAll().size();

        // Create the TipoOcupacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoOcupacionMockMvc.perform(put("/api/tipo-ocupacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoOcupacion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoOcupacion in the database
        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoOcupacion() throws Exception {
        // Initialize the database
        tipoOcupacionService.save(tipoOcupacion);

        int databaseSizeBeforeDelete = tipoOcupacionRepository.findAll().size();

        // Delete the tipoOcupacion
        restTipoOcupacionMockMvc.perform(delete("/api/tipo-ocupacions/{id}", tipoOcupacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoOcupacion> tipoOcupacionList = tipoOcupacionRepository.findAll();
        assertThat(tipoOcupacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
