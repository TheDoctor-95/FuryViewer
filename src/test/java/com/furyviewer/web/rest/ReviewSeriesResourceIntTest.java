package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.ReviewSeries;
import com.furyviewer.repository.ReviewSeriesRepository;
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
 * Test class for the ReviewSeriesResource REST controller.
 *
 * @see ReviewSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class ReviewSeriesResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW = "BBBBBBBBBB";

    @Autowired
    private ReviewSeriesRepository reviewSeriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReviewSeriesMockMvc;

    private ReviewSeries reviewSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewSeriesResource reviewSeriesResource = new ReviewSeriesResource(reviewSeriesRepository);
        this.restReviewSeriesMockMvc = MockMvcBuilders.standaloneSetup(reviewSeriesResource)
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
    public static ReviewSeries createEntity(EntityManager em) {
        ReviewSeries reviewSeries = new ReviewSeries()
            .date(DEFAULT_DATE)
            .title(DEFAULT_TITLE)
            .review(DEFAULT_REVIEW);
        return reviewSeries;
    }

    @Before
    public void initTest() {
        reviewSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewSeries() throws Exception {
        int databaseSizeBeforeCreate = reviewSeriesRepository.findAll().size();

        // Create the ReviewSeries
        restReviewSeriesMockMvc.perform(post("/api/review-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSeries)))
            .andExpect(status().isCreated());

        // Validate the ReviewSeries in the database
        List<ReviewSeries> reviewSeriesList = reviewSeriesRepository.findAll();
        assertThat(reviewSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewSeries testReviewSeries = reviewSeriesList.get(reviewSeriesList.size() - 1);
        assertThat(testReviewSeries.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReviewSeries.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReviewSeries.getReview()).isEqualTo(DEFAULT_REVIEW);
    }

    @Test
    @Transactional
    public void createReviewSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewSeriesRepository.findAll().size();

        // Create the ReviewSeries with an existing ID
        reviewSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewSeriesMockMvc.perform(post("/api/review-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSeries)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewSeries in the database
        List<ReviewSeries> reviewSeriesList = reviewSeriesRepository.findAll();
        assertThat(reviewSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReviewSeries() throws Exception {
        // Initialize the database
        reviewSeriesRepository.saveAndFlush(reviewSeries);

        // Get all the reviewSeriesList
        restReviewSeriesMockMvc.perform(get("/api/review-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW.toString())));
    }

    @Test
    @Transactional
    public void getReviewSeries() throws Exception {
        // Initialize the database
        reviewSeriesRepository.saveAndFlush(reviewSeries);

        // Get the reviewSeries
        restReviewSeriesMockMvc.perform(get("/api/review-series/{id}", reviewSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviewSeries.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReviewSeries() throws Exception {
        // Get the reviewSeries
        restReviewSeriesMockMvc.perform(get("/api/review-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewSeries() throws Exception {
        // Initialize the database
        reviewSeriesRepository.saveAndFlush(reviewSeries);
        int databaseSizeBeforeUpdate = reviewSeriesRepository.findAll().size();

        // Update the reviewSeries
        ReviewSeries updatedReviewSeries = reviewSeriesRepository.findOne(reviewSeries.getId());
        updatedReviewSeries
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .review(UPDATED_REVIEW);

        restReviewSeriesMockMvc.perform(put("/api/review-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReviewSeries)))
            .andExpect(status().isOk());

        // Validate the ReviewSeries in the database
        List<ReviewSeries> reviewSeriesList = reviewSeriesRepository.findAll();
        assertThat(reviewSeriesList).hasSize(databaseSizeBeforeUpdate);
        ReviewSeries testReviewSeries = reviewSeriesList.get(reviewSeriesList.size() - 1);
        assertThat(testReviewSeries.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReviewSeries.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReviewSeries.getReview()).isEqualTo(UPDATED_REVIEW);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewSeries() throws Exception {
        int databaseSizeBeforeUpdate = reviewSeriesRepository.findAll().size();

        // Create the ReviewSeries

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReviewSeriesMockMvc.perform(put("/api/review-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSeries)))
            .andExpect(status().isCreated());

        // Validate the ReviewSeries in the database
        List<ReviewSeries> reviewSeriesList = reviewSeriesRepository.findAll();
        assertThat(reviewSeriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReviewSeries() throws Exception {
        // Initialize the database
        reviewSeriesRepository.saveAndFlush(reviewSeries);
        int databaseSizeBeforeDelete = reviewSeriesRepository.findAll().size();

        // Get the reviewSeries
        restReviewSeriesMockMvc.perform(delete("/api/review-series/{id}", reviewSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReviewSeries> reviewSeriesList = reviewSeriesRepository.findAll();
        assertThat(reviewSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewSeries.class);
        ReviewSeries reviewSeries1 = new ReviewSeries();
        reviewSeries1.setId(1L);
        ReviewSeries reviewSeries2 = new ReviewSeries();
        reviewSeries2.setId(reviewSeries1.getId());
        assertThat(reviewSeries1).isEqualTo(reviewSeries2);
        reviewSeries2.setId(2L);
        assertThat(reviewSeries1).isNotEqualTo(reviewSeries2);
        reviewSeries1.setId(null);
        assertThat(reviewSeries1).isNotEqualTo(reviewSeries2);
    }
}
