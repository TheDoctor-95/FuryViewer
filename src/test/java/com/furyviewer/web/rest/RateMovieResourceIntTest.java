package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.RateMovie;
import com.furyviewer.repository.RateMovieRepository;
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
 * Test class for the RateMovieResource REST controller.
 *
 * @see RateMovieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class RateMovieResourceIntTest {

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RateMovieRepository rateMovieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRateMovieMockMvc;

    private RateMovie rateMovie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RateMovieResource rateMovieResource = new RateMovieResource(rateMovieRepository);
        this.restRateMovieMockMvc = MockMvcBuilders.standaloneSetup(rateMovieResource)
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
    public static RateMovie createEntity(EntityManager em) {
        RateMovie rateMovie = new RateMovie()
            .rate(DEFAULT_RATE)
            .date(DEFAULT_DATE);
        return rateMovie;
    }

    @Before
    public void initTest() {
        rateMovie = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateMovie() throws Exception {
        int databaseSizeBeforeCreate = rateMovieRepository.findAll().size();

        // Create the RateMovie
        restRateMovieMockMvc.perform(post("/api/rate-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateMovie)))
            .andExpect(status().isCreated());

        // Validate the RateMovie in the database
        List<RateMovie> rateMovieList = rateMovieRepository.findAll();
        assertThat(rateMovieList).hasSize(databaseSizeBeforeCreate + 1);
        RateMovie testRateMovie = rateMovieList.get(rateMovieList.size() - 1);
        assertThat(testRateMovie.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testRateMovie.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createRateMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateMovieRepository.findAll().size();

        // Create the RateMovie with an existing ID
        rateMovie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateMovieMockMvc.perform(post("/api/rate-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateMovie)))
            .andExpect(status().isBadRequest());

        // Validate the RateMovie in the database
        List<RateMovie> rateMovieList = rateMovieRepository.findAll();
        assertThat(rateMovieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRateMovies() throws Exception {
        // Initialize the database
        rateMovieRepository.saveAndFlush(rateMovie);

        // Get all the rateMovieList
        restRateMovieMockMvc.perform(get("/api/rate-movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateMovie.getId().intValue())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getRateMovie() throws Exception {
        // Initialize the database
        rateMovieRepository.saveAndFlush(rateMovie);

        // Get the rateMovie
        restRateMovieMockMvc.perform(get("/api/rate-movies/{id}", rateMovie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateMovie.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRateMovie() throws Exception {
        // Get the rateMovie
        restRateMovieMockMvc.perform(get("/api/rate-movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateMovie() throws Exception {
        // Initialize the database
        rateMovieRepository.saveAndFlush(rateMovie);
        int databaseSizeBeforeUpdate = rateMovieRepository.findAll().size();

        // Update the rateMovie
        RateMovie updatedRateMovie = rateMovieRepository.findOne(rateMovie.getId());
        updatedRateMovie
            .rate(UPDATED_RATE)
            .date(UPDATED_DATE);

        restRateMovieMockMvc.perform(put("/api/rate-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRateMovie)))
            .andExpect(status().isOk());

        // Validate the RateMovie in the database
        List<RateMovie> rateMovieList = rateMovieRepository.findAll();
        assertThat(rateMovieList).hasSize(databaseSizeBeforeUpdate);
        RateMovie testRateMovie = rateMovieList.get(rateMovieList.size() - 1);
        assertThat(testRateMovie.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testRateMovie.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRateMovie() throws Exception {
        int databaseSizeBeforeUpdate = rateMovieRepository.findAll().size();

        // Create the RateMovie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRateMovieMockMvc.perform(put("/api/rate-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateMovie)))
            .andExpect(status().isCreated());

        // Validate the RateMovie in the database
        List<RateMovie> rateMovieList = rateMovieRepository.findAll();
        assertThat(rateMovieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRateMovie() throws Exception {
        // Initialize the database
        rateMovieRepository.saveAndFlush(rateMovie);
        int databaseSizeBeforeDelete = rateMovieRepository.findAll().size();

        // Get the rateMovie
        restRateMovieMockMvc.perform(delete("/api/rate-movies/{id}", rateMovie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RateMovie> rateMovieList = rateMovieRepository.findAll();
        assertThat(rateMovieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateMovie.class);
        RateMovie rateMovie1 = new RateMovie();
        rateMovie1.setId(1L);
        RateMovie rateMovie2 = new RateMovie();
        rateMovie2.setId(rateMovie1.getId());
        assertThat(rateMovie1).isEqualTo(rateMovie2);
        rateMovie2.setId(2L);
        assertThat(rateMovie1).isNotEqualTo(rateMovie2);
        rateMovie1.setId(null);
        assertThat(rateMovie1).isNotEqualTo(rateMovie2);
    }
}
