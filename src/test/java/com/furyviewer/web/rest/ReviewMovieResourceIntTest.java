package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.ReviewMovie;
import com.furyviewer.repository.ReviewMovieRepository;
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
 * Test class for the ReviewMovieResource REST controller.
 *
 * @see ReviewMovieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class ReviewMovieResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_REVIEW = "BBBBBBBBBB";

    @Autowired
    private ReviewMovieRepository reviewMovieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReviewMovieMockMvc;

    private ReviewMovie reviewMovie;

    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final ReviewMovieResource reviewMovieResource = new ReviewMovieResource(reviewMovieRepository, userRepository);
//        this.restReviewMovieMockMvc = MockMvcBuilders.standaloneSetup(reviewMovieResource)
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
    public static ReviewMovie createEntity(EntityManager em) {
        ReviewMovie reviewMovie = new ReviewMovie()
            .date(DEFAULT_DATE)
            .title(DEFAULT_TITLE)
            .review(DEFAULT_REVIEW);
        return reviewMovie;
    }

    @Before
    public void initTest() {
        reviewMovie = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewMovie() throws Exception {
        int databaseSizeBeforeCreate = reviewMovieRepository.findAll().size();

        // Create the ReviewMovie
        restReviewMovieMockMvc.perform(post("/api/review-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewMovie)))
            .andExpect(status().isCreated());

        // Validate the ReviewMovie in the database
        List<ReviewMovie> reviewMovieList = reviewMovieRepository.findAll();
        assertThat(reviewMovieList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewMovie testReviewMovie = reviewMovieList.get(reviewMovieList.size() - 1);
        assertThat(testReviewMovie.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReviewMovie.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReviewMovie.getReview()).isEqualTo(DEFAULT_REVIEW);
    }

    @Test
    @Transactional
    public void createReviewMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewMovieRepository.findAll().size();

        // Create the ReviewMovie with an existing ID
        reviewMovie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewMovieMockMvc.perform(post("/api/review-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewMovie)))
            .andExpect(status().isBadRequest());

        // Validate the ReviewMovie in the database
        List<ReviewMovie> reviewMovieList = reviewMovieRepository.findAll();
        assertThat(reviewMovieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReviewMovies() throws Exception {
        // Initialize the database
        reviewMovieRepository.saveAndFlush(reviewMovie);

        // Get all the reviewMovieList
        restReviewMovieMockMvc.perform(get("/api/review-movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewMovie.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].review").value(hasItem(DEFAULT_REVIEW.toString())));
    }

    @Test
    @Transactional
    public void getReviewMovie() throws Exception {
        // Initialize the database
        reviewMovieRepository.saveAndFlush(reviewMovie);

        // Get the reviewMovie
        restReviewMovieMockMvc.perform(get("/api/review-movies/{id}", reviewMovie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviewMovie.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.review").value(DEFAULT_REVIEW.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReviewMovie() throws Exception {
        // Get the reviewMovie
        restReviewMovieMockMvc.perform(get("/api/review-movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewMovie() throws Exception {
        // Initialize the database
        reviewMovieRepository.saveAndFlush(reviewMovie);
        int databaseSizeBeforeUpdate = reviewMovieRepository.findAll().size();

        // Update the reviewMovie
        ReviewMovie updatedReviewMovie = reviewMovieRepository.findOne(reviewMovie.getId());
        updatedReviewMovie
            .date(UPDATED_DATE)
            .title(UPDATED_TITLE)
            .review(UPDATED_REVIEW);

        restReviewMovieMockMvc.perform(put("/api/review-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReviewMovie)))
            .andExpect(status().isOk());

        // Validate the ReviewMovie in the database
        List<ReviewMovie> reviewMovieList = reviewMovieRepository.findAll();
        assertThat(reviewMovieList).hasSize(databaseSizeBeforeUpdate);
        ReviewMovie testReviewMovie = reviewMovieList.get(reviewMovieList.size() - 1);
        assertThat(testReviewMovie.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReviewMovie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReviewMovie.getReview()).isEqualTo(UPDATED_REVIEW);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewMovie() throws Exception {
        int databaseSizeBeforeUpdate = reviewMovieRepository.findAll().size();

        // Create the ReviewMovie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReviewMovieMockMvc.perform(put("/api/review-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewMovie)))
            .andExpect(status().isCreated());

        // Validate the ReviewMovie in the database
        List<ReviewMovie> reviewMovieList = reviewMovieRepository.findAll();
        assertThat(reviewMovieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReviewMovie() throws Exception {
        // Initialize the database
        reviewMovieRepository.saveAndFlush(reviewMovie);
        int databaseSizeBeforeDelete = reviewMovieRepository.findAll().size();

        // Get the reviewMovie
        restReviewMovieMockMvc.perform(delete("/api/review-movies/{id}", reviewMovie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReviewMovie> reviewMovieList = reviewMovieRepository.findAll();
        assertThat(reviewMovieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewMovie.class);
        ReviewMovie reviewMovie1 = new ReviewMovie();
        reviewMovie1.setId(1L);
        ReviewMovie reviewMovie2 = new ReviewMovie();
        reviewMovie2.setId(reviewMovie1.getId());
        assertThat(reviewMovie1).isEqualTo(reviewMovie2);
        reviewMovie2.setId(2L);
        assertThat(reviewMovie1).isNotEqualTo(reviewMovie2);
        reviewMovie1.setId(null);
        assertThat(reviewMovie1).isNotEqualTo(reviewMovie2);
    }
}
