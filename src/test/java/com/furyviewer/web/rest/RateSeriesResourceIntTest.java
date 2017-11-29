package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.RateSeries;
import com.furyviewer.repository.RateSeriesRepository;
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
 * Test class for the RateSeriesResource REST controller.
 *
 * @see RateSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class RateSeriesResourceIntTest {

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RateSeriesRepository rateSeriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRateSeriesMockMvc;

    private RateSeries rateSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RateSeriesResource rateSeriesResource = new RateSeriesResource(rateSeriesRepository);
        this.restRateSeriesMockMvc = MockMvcBuilders.standaloneSetup(rateSeriesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RateSeries createEntity(EntityManager em) {
        RateSeries rateSeries = new RateSeries()
            .rate(DEFAULT_RATE)
            .date(DEFAULT_DATE);
        return rateSeries;
    }

    @Before
    public void initTest() {
        rateSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateSeries() throws Exception {
        int databaseSizeBeforeCreate = rateSeriesRepository.findAll().size();

        // Create the RateSeries
        restRateSeriesMockMvc.perform(post("/api/rate-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateSeries)))
            .andExpect(status().isCreated());

        // Validate the RateSeries in the database
        List<RateSeries> rateSeriesList = rateSeriesRepository.findAll();
        assertThat(rateSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        RateSeries testRateSeries = rateSeriesList.get(rateSeriesList.size() - 1);
        assertThat(testRateSeries.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testRateSeries.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createRateSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateSeriesRepository.findAll().size();

        // Create the RateSeries with an existing ID
        rateSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateSeriesMockMvc.perform(post("/api/rate-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateSeries)))
            .andExpect(status().isBadRequest());

        // Validate the RateSeries in the database
        List<RateSeries> rateSeriesList = rateSeriesRepository.findAll();
        assertThat(rateSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRateSeries() throws Exception {
        // Initialize the database
        rateSeriesRepository.saveAndFlush(rateSeries);

        // Get all the rateSeriesList
        restRateSeriesMockMvc.perform(get("/api/rate-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getRateSeries() throws Exception {
        // Initialize the database
        rateSeriesRepository.saveAndFlush(rateSeries);

        // Get the rateSeries
        restRateSeriesMockMvc.perform(get("/api/rate-series/{id}", rateSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateSeries.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRateSeries() throws Exception {
        // Get the rateSeries
        restRateSeriesMockMvc.perform(get("/api/rate-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateSeries() throws Exception {
        // Initialize the database
        rateSeriesRepository.saveAndFlush(rateSeries);
        int databaseSizeBeforeUpdate = rateSeriesRepository.findAll().size();

        // Update the rateSeries
        RateSeries updatedRateSeries = rateSeriesRepository.findOne(rateSeries.getId());
        updatedRateSeries
            .rate(UPDATED_RATE)
            .date(UPDATED_DATE);

        restRateSeriesMockMvc.perform(put("/api/rate-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRateSeries)))
            .andExpect(status().isOk());

        // Validate the RateSeries in the database
        List<RateSeries> rateSeriesList = rateSeriesRepository.findAll();
        assertThat(rateSeriesList).hasSize(databaseSizeBeforeUpdate);
        RateSeries testRateSeries = rateSeriesList.get(rateSeriesList.size() - 1);
        assertThat(testRateSeries.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testRateSeries.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRateSeries() throws Exception {
        int databaseSizeBeforeUpdate = rateSeriesRepository.findAll().size();

        // Create the RateSeries

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRateSeriesMockMvc.perform(put("/api/rate-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateSeries)))
            .andExpect(status().isCreated());

        // Validate the RateSeries in the database
        List<RateSeries> rateSeriesList = rateSeriesRepository.findAll();
        assertThat(rateSeriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRateSeries() throws Exception {
        // Initialize the database
        rateSeriesRepository.saveAndFlush(rateSeries);
        int databaseSizeBeforeDelete = rateSeriesRepository.findAll().size();

        // Get the rateSeries
        restRateSeriesMockMvc.perform(delete("/api/rate-series/{id}", rateSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RateSeries> rateSeriesList = rateSeriesRepository.findAll();
        assertThat(rateSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateSeries.class);
        RateSeries rateSeries1 = new RateSeries();
        rateSeries1.setId(1L);
        RateSeries rateSeries2 = new RateSeries();
        rateSeries2.setId(rateSeries1.getId());
        assertThat(rateSeries1).isEqualTo(rateSeries2);
        rateSeries2.setId(2L);
        assertThat(rateSeries1).isNotEqualTo(rateSeries2);
        rateSeries1.setId(null);
        assertThat(rateSeries1).isNotEqualTo(rateSeries2);
    }
}
