package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.SeriesStats;
import com.furyviewer.repository.SeriesStatsRepository;
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

import com.furyviewer.domain.enumeration.SeriesStatsEnum;
/**
 * Test class for the SeriesStatsResource REST controller.
 *
 * @see SeriesStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class SeriesStatsResourceIntTest {

    private static final SeriesStatsEnum DEFAULT_STATUS = SeriesStatsEnum.PENDING;
    private static final SeriesStatsEnum UPDATED_STATUS = SeriesStatsEnum.FOLLOWING;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SeriesStatsRepository seriesStatsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeriesStatsMockMvc;

    private SeriesStats seriesStats;

    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final SeriesStatsResource seriesStatsResource = new SeriesStatsResource(seriesStatsRepository, seriesRepository);
//        this.restSeriesStatsMockMvc = MockMvcBuilders.standaloneSetup(seriesStatsResource)
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
    public static SeriesStats createEntity(EntityManager em) {
        SeriesStats seriesStats = new SeriesStats()
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE);
        return seriesStats;
    }

    @Before
    public void initTest() {
        seriesStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeriesStats() throws Exception {
        int databaseSizeBeforeCreate = seriesStatsRepository.findAll().size();

        // Create the SeriesStats
        restSeriesStatsMockMvc.perform(post("/api/series-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seriesStats)))
            .andExpect(status().isCreated());

        // Validate the SeriesStats in the database
        List<SeriesStats> seriesStatsList = seriesStatsRepository.findAll();
        assertThat(seriesStatsList).hasSize(databaseSizeBeforeCreate + 1);
        SeriesStats testSeriesStats = seriesStatsList.get(seriesStatsList.size() - 1);
        assertThat(testSeriesStats.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSeriesStats.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createSeriesStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seriesStatsRepository.findAll().size();

        // Create the SeriesStats with an existing ID
        seriesStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeriesStatsMockMvc.perform(post("/api/series-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seriesStats)))
            .andExpect(status().isBadRequest());

        // Validate the SeriesStats in the database
        List<SeriesStats> seriesStatsList = seriesStatsRepository.findAll();
        assertThat(seriesStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeriesStats() throws Exception {
        // Initialize the database
        seriesStatsRepository.saveAndFlush(seriesStats);

        // Get all the seriesStatsList
        restSeriesStatsMockMvc.perform(get("/api/series-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seriesStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getSeriesStats() throws Exception {
        // Initialize the database
        seriesStatsRepository.saveAndFlush(seriesStats);

        // Get the seriesStats
        restSeriesStatsMockMvc.perform(get("/api/series-stats/{id}", seriesStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seriesStats.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSeriesStats() throws Exception {
        // Get the seriesStats
        restSeriesStatsMockMvc.perform(get("/api/series-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeriesStats() throws Exception {
        // Initialize the database
        seriesStatsRepository.saveAndFlush(seriesStats);
        int databaseSizeBeforeUpdate = seriesStatsRepository.findAll().size();

        // Update the seriesStats
        SeriesStats updatedSeriesStats = seriesStatsRepository.findOne(seriesStats.getId());
        updatedSeriesStats
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE);

        restSeriesStatsMockMvc.perform(put("/api/series-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeriesStats)))
            .andExpect(status().isOk());

        // Validate the SeriesStats in the database
        List<SeriesStats> seriesStatsList = seriesStatsRepository.findAll();
        assertThat(seriesStatsList).hasSize(databaseSizeBeforeUpdate);
        SeriesStats testSeriesStats = seriesStatsList.get(seriesStatsList.size() - 1);
        assertThat(testSeriesStats.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSeriesStats.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeriesStats() throws Exception {
        int databaseSizeBeforeUpdate = seriesStatsRepository.findAll().size();

        // Create the SeriesStats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSeriesStatsMockMvc.perform(put("/api/series-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seriesStats)))
            .andExpect(status().isCreated());

        // Validate the SeriesStats in the database
        List<SeriesStats> seriesStatsList = seriesStatsRepository.findAll();
        assertThat(seriesStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeriesStats() throws Exception {
        // Initialize the database
        seriesStatsRepository.saveAndFlush(seriesStats);
        int databaseSizeBeforeDelete = seriesStatsRepository.findAll().size();

        // Get the seriesStats
        restSeriesStatsMockMvc.perform(delete("/api/series-stats/{id}", seriesStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SeriesStats> seriesStatsList = seriesStatsRepository.findAll();
        assertThat(seriesStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeriesStats.class);
        SeriesStats seriesStats1 = new SeriesStats();
        seriesStats1.setId(1L);
        SeriesStats seriesStats2 = new SeriesStats();
        seriesStats2.setId(seriesStats1.getId());
        assertThat(seriesStats1).isEqualTo(seriesStats2);
        seriesStats2.setId(2L);
        assertThat(seriesStats1).isNotEqualTo(seriesStats2);
        seriesStats1.setId(null);
        assertThat(seriesStats1).isNotEqualTo(seriesStats2);
    }
}
