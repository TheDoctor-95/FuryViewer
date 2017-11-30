package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.MovieStats;
import com.furyviewer.repository.MovieStatsRepository;
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

import com.furyviewer.domain.enumeration.MovieStatsEnum;
/**
 * Test class for the MovieStatsResource REST controller.
 *
 * @see MovieStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class MovieStatsResourceIntTest {

    private static final MovieStatsEnum DEFAULT_STATUS = MovieStatsEnum.PENDING;
    private static final MovieStatsEnum UPDATED_STATUS = MovieStatsEnum.SEEN;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MovieStatsRepository movieStatsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovieStatsMockMvc;

    private MovieStats movieStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovieStatsResource movieStatsResource = new MovieStatsResource(movieStatsRepository);
        this.restMovieStatsMockMvc = MockMvcBuilders.standaloneSetup(movieStatsResource)
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
    public static MovieStats createEntity(EntityManager em) {
        MovieStats movieStats = new MovieStats()
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE);
        return movieStats;
    }

    @Before
    public void initTest() {
        movieStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovieStats() throws Exception {
        int databaseSizeBeforeCreate = movieStatsRepository.findAll().size();

        // Create the MovieStats
        restMovieStatsMockMvc.perform(post("/api/movie-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStats)))
            .andExpect(status().isCreated());

        // Validate the MovieStats in the database
        List<MovieStats> movieStatsList = movieStatsRepository.findAll();
        assertThat(movieStatsList).hasSize(databaseSizeBeforeCreate + 1);
        MovieStats testMovieStats = movieStatsList.get(movieStatsList.size() - 1);
        assertThat(testMovieStats.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMovieStats.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMovieStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movieStatsRepository.findAll().size();

        // Create the MovieStats with an existing ID
        movieStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovieStatsMockMvc.perform(post("/api/movie-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStats)))
            .andExpect(status().isBadRequest());

        // Validate the MovieStats in the database
        List<MovieStats> movieStatsList = movieStatsRepository.findAll();
        assertThat(movieStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMovieStats() throws Exception {
        // Initialize the database
        movieStatsRepository.saveAndFlush(movieStats);

        // Get all the movieStatsList
        restMovieStatsMockMvc.perform(get("/api/movie-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movieStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getMovieStats() throws Exception {
        // Initialize the database
        movieStatsRepository.saveAndFlush(movieStats);

        // Get the movieStats
        restMovieStatsMockMvc.perform(get("/api/movie-stats/{id}", movieStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movieStats.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingMovieStats() throws Exception {
        // Get the movieStats
        restMovieStatsMockMvc.perform(get("/api/movie-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovieStats() throws Exception {
        // Initialize the database
        movieStatsRepository.saveAndFlush(movieStats);
        int databaseSizeBeforeUpdate = movieStatsRepository.findAll().size();

        // Update the movieStats
        MovieStats updatedMovieStats = movieStatsRepository.findOne(movieStats.getId());
        updatedMovieStats
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE);

        restMovieStatsMockMvc.perform(put("/api/movie-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovieStats)))
            .andExpect(status().isOk());

        // Validate the MovieStats in the database
        List<MovieStats> movieStatsList = movieStatsRepository.findAll();
        assertThat(movieStatsList).hasSize(databaseSizeBeforeUpdate);
        MovieStats testMovieStats = movieStatsList.get(movieStatsList.size() - 1);
        assertThat(testMovieStats.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMovieStats.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMovieStats() throws Exception {
        int databaseSizeBeforeUpdate = movieStatsRepository.findAll().size();

        // Create the MovieStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovieStatsMockMvc.perform(put("/api/movie-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movieStats)))
            .andExpect(status().isCreated());

        // Validate the MovieStats in the database
        List<MovieStats> movieStatsList = movieStatsRepository.findAll();
        assertThat(movieStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovieStats() throws Exception {
        // Initialize the database
        movieStatsRepository.saveAndFlush(movieStats);
        int databaseSizeBeforeDelete = movieStatsRepository.findAll().size();

        // Get the movieStats
        restMovieStatsMockMvc.perform(delete("/api/movie-stats/{id}", movieStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovieStats> movieStatsList = movieStatsRepository.findAll();
        assertThat(movieStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovieStats.class);
        MovieStats movieStats1 = new MovieStats();
        movieStats1.setId(1L);
        MovieStats movieStats2 = new MovieStats();
        movieStats2.setId(movieStats1.getId());
        assertThat(movieStats1).isEqualTo(movieStats2);
        movieStats2.setId(2L);
        assertThat(movieStats1).isNotEqualTo(movieStats2);
        movieStats1.setId(null);
        assertThat(movieStats1).isNotEqualTo(movieStats2);
    }
}
