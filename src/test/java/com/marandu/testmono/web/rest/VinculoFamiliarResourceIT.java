package com.marandu.testmono.web.rest;

import com.marandu.testmono.Testmono04App;
import com.marandu.testmono.domain.VinculoFamiliar;
import com.marandu.testmono.repository.VinculoFamiliarRepository;
import com.marandu.testmono.service.VinculoFamiliarService;
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
 * Integration tests for the {@link VinculoFamiliarResource} REST controller.
 */
@SpringBootTest(classes = Testmono04App.class)
public class VinculoFamiliarResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private VinculoFamiliarRepository vinculoFamiliarRepository;

    @Autowired
    private VinculoFamiliarService vinculoFamiliarService;

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

    private MockMvc restVinculoFamiliarMockMvc;

    private VinculoFamiliar vinculoFamiliar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VinculoFamiliarResource vinculoFamiliarResource = new VinculoFamiliarResource(vinculoFamiliarService);
        this.restVinculoFamiliarMockMvc = MockMvcBuilders.standaloneSetup(vinculoFamiliarResource)
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
    public static VinculoFamiliar createEntity(EntityManager em) {
        VinculoFamiliar vinculoFamiliar = new VinculoFamiliar()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return vinculoFamiliar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VinculoFamiliar createUpdatedEntity(EntityManager em) {
        VinculoFamiliar vinculoFamiliar = new VinculoFamiliar()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        return vinculoFamiliar;
    }

    @BeforeEach
    public void initTest() {
        vinculoFamiliar = createEntity(em);
    }

    @Test
    @Transactional
    public void createVinculoFamiliar() throws Exception {
        int databaseSizeBeforeCreate = vinculoFamiliarRepository.findAll().size();

        // Create the VinculoFamiliar
        restVinculoFamiliarMockMvc.perform(post("/api/vinculo-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculoFamiliar)))
            .andExpect(status().isCreated());

        // Validate the VinculoFamiliar in the database
        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeCreate + 1);
        VinculoFamiliar testVinculoFamiliar = vinculoFamiliarList.get(vinculoFamiliarList.size() - 1);
        assertThat(testVinculoFamiliar.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVinculoFamiliar.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createVinculoFamiliarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vinculoFamiliarRepository.findAll().size();

        // Create the VinculoFamiliar with an existing ID
        vinculoFamiliar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinculoFamiliarMockMvc.perform(post("/api/vinculo-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculoFamiliar)))
            .andExpect(status().isBadRequest());

        // Validate the VinculoFamiliar in the database
        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = vinculoFamiliarRepository.findAll().size();
        // set the field null
        vinculoFamiliar.setNombre(null);

        // Create the VinculoFamiliar, which fails.

        restVinculoFamiliarMockMvc.perform(post("/api/vinculo-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculoFamiliar)))
            .andExpect(status().isBadRequest());

        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVinculoFamiliars() throws Exception {
        // Initialize the database
        vinculoFamiliarRepository.saveAndFlush(vinculoFamiliar);

        // Get all the vinculoFamiliarList
        restVinculoFamiliarMockMvc.perform(get("/api/vinculo-familiars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculoFamiliar.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVinculoFamiliar() throws Exception {
        // Initialize the database
        vinculoFamiliarRepository.saveAndFlush(vinculoFamiliar);

        // Get the vinculoFamiliar
        restVinculoFamiliarMockMvc.perform(get("/api/vinculo-familiars/{id}", vinculoFamiliar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vinculoFamiliar.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVinculoFamiliar() throws Exception {
        // Get the vinculoFamiliar
        restVinculoFamiliarMockMvc.perform(get("/api/vinculo-familiars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVinculoFamiliar() throws Exception {
        // Initialize the database
        vinculoFamiliarService.save(vinculoFamiliar);

        int databaseSizeBeforeUpdate = vinculoFamiliarRepository.findAll().size();

        // Update the vinculoFamiliar
        VinculoFamiliar updatedVinculoFamiliar = vinculoFamiliarRepository.findById(vinculoFamiliar.getId()).get();
        // Disconnect from session so that the updates on updatedVinculoFamiliar are not directly saved in db
        em.detach(updatedVinculoFamiliar);
        updatedVinculoFamiliar
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);

        restVinculoFamiliarMockMvc.perform(put("/api/vinculo-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVinculoFamiliar)))
            .andExpect(status().isOk());

        // Validate the VinculoFamiliar in the database
        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeUpdate);
        VinculoFamiliar testVinculoFamiliar = vinculoFamiliarList.get(vinculoFamiliarList.size() - 1);
        assertThat(testVinculoFamiliar.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVinculoFamiliar.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingVinculoFamiliar() throws Exception {
        int databaseSizeBeforeUpdate = vinculoFamiliarRepository.findAll().size();

        // Create the VinculoFamiliar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculoFamiliarMockMvc.perform(put("/api/vinculo-familiars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vinculoFamiliar)))
            .andExpect(status().isBadRequest());

        // Validate the VinculoFamiliar in the database
        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVinculoFamiliar() throws Exception {
        // Initialize the database
        vinculoFamiliarService.save(vinculoFamiliar);

        int databaseSizeBeforeDelete = vinculoFamiliarRepository.findAll().size();

        // Delete the vinculoFamiliar
        restVinculoFamiliarMockMvc.perform(delete("/api/vinculo-familiars/{id}", vinculoFamiliar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VinculoFamiliar> vinculoFamiliarList = vinculoFamiliarRepository.findAll();
        assertThat(vinculoFamiliarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
