package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.HatredSeries;
import com.furyviewer.repository.HatredSeriesRepository;
import com.furyviewer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.furyviewer.web.rest.TestUtil.sameInstant;
import static com.furyviewer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HatredSeriesResource REST controller.
 *
 * @see HatredSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class HatredSeriesResourceIntTest {

    private static final Boolean DEFAULT_HATED = false;
    private static final Boolean UPDATED_HATED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HatredSeriesRepository hatredSeriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHatredSeriesMockMvc;

    private HatredSeries hatredSeries;

    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final HatredSeriesResource hatredSeriesResource = new HatredSeriesResource(hatredSeriesRepository);
//        this.restHatredSeriesMockMvc = MockMvcBuilders.standaloneSetup(hatredSeriesResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter).build();
//    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HatredSeries createEntity(EntityManager em) {
        HatredSeries hatredSeries = new HatredSeries()
            .hated(DEFAULT_HATED)
            .date(DEFAULT_DATE);
        return hatredSeries;
    }

    @Before
    public void initTest() {
        hatredSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createHatredSeries() throws Exception {
        int databaseSizeBeforeCreate = hatredSeriesRepository.findAll().size();

        // Create the HatredSeries
        restHatredSeriesMockMvc.perform(post("/api/hatred-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredSeries)))
            .andExpect(status().isCreated());

        // Validate the HatredSeries in the database
        List<HatredSeries> hatredSeriesList = hatredSeriesRepository.findAll();
        assertThat(hatredSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        HatredSeries testHatredSeries = hatredSeriesList.get(hatredSeriesList.size() - 1);
        assertThat(testHatredSeries.isHated()).isEqualTo(DEFAULT_HATED);
        assertThat(testHatredSeries.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createHatredSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hatredSeriesRepository.findAll().size();

        // Create the HatredSeries with an existing ID
        hatredSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHatredSeriesMockMvc.perform(post("/api/hatred-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredSeries)))
            .andExpect(status().isBadRequest());

        // Validate the HatredSeries in the database
        List<HatredSeries> hatredSeriesList = hatredSeriesRepository.findAll();
        assertThat(hatredSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHatredSeries() throws Exception {
        // Initialize the database
        hatredSeriesRepository.saveAndFlush(hatredSeries);

        // Get all the hatredSeriesList
        restHatredSeriesMockMvc.perform(get("/api/hatred-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hatredSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].hated").value(hasItem(DEFAULT_HATED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getHatredSeries() throws Exception {
        // Initialize the database
        hatredSeriesRepository.saveAndFlush(hatredSeries);

        // Get the hatredSeries
        restHatredSeriesMockMvc.perform(get("/api/hatred-series/{id}", hatredSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hatredSeries.getId().intValue()))
            .andExpect(jsonPath("$.hated").value(DEFAULT_HATED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingHatredSeries() throws Exception {
        // Get the hatredSeries
        restHatredSeriesMockMvc.perform(get("/api/hatred-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHatredSeries() throws Exception {
        // Initialize the database
        hatredSeriesRepository.saveAndFlush(hatredSeries);
        int databaseSizeBeforeUpdate = hatredSeriesRepository.findAll().size();

        // Update the hatredSeries
        HatredSeries updatedHatredSeries = hatredSeriesRepository.findOne(hatredSeries.getId());
        updatedHatredSeries
            .hated(UPDATED_HATED)
            .date(UPDATED_DATE);

        restHatredSeriesMockMvc.perform(put("/api/hatred-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHatredSeries)))
            .andExpect(status().isOk());

        // Validate the HatredSeries in the database
        List<HatredSeries> hatredSeriesList = hatredSeriesRepository.findAll();
        assertThat(hatredSeriesList).hasSize(databaseSizeBeforeUpdate);
        HatredSeries testHatredSeries = hatredSeriesList.get(hatredSeriesList.size() - 1);
        assertThat(testHatredSeries.isHated()).isEqualTo(UPDATED_HATED);
        assertThat(testHatredSeries.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHatredSeries() throws Exception {
        int databaseSizeBeforeUpdate = hatredSeriesRepository.findAll().size();

        // Create the HatredSeries

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHatredSeriesMockMvc.perform(put("/api/hatred-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredSeries)))
            .andExpect(status().isCreated());

        // Validate the HatredSeries in the database
        List<HatredSeries> hatredSeriesList = hatredSeriesRepository.findAll();
        assertThat(hatredSeriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHatredSeries() throws Exception {
        // Initialize the database
        hatredSeriesRepository.saveAndFlush(hatredSeries);
        int databaseSizeBeforeDelete = hatredSeriesRepository.findAll().size();

        // Get the hatredSeries
        restHatredSeriesMockMvc.perform(delete("/api/hatred-series/{id}", hatredSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HatredSeries> hatredSeriesList = hatredSeriesRepository.findAll();
        assertThat(hatredSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HatredSeries.class);
        HatredSeries hatredSeries1 = new HatredSeries();
        hatredSeries1.setId(1L);
        HatredSeries hatredSeries2 = new HatredSeries();
        hatredSeries2.setId(hatredSeries1.getId());
        assertThat(hatredSeries1).isEqualTo(hatredSeries2);
        hatredSeries2.setId(2L);
        assertThat(hatredSeries1).isNotEqualTo(hatredSeries2);
        hatredSeries1.setId(null);
        assertThat(hatredSeries1).isNotEqualTo(hatredSeries2);
    }
}
