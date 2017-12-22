package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.FavouriteMovie;
import com.furyviewer.repository.FavouriteMovieRepository;
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
 * Test class for the FavouriteMovieResource REST controller.
 *
 * @see FavouriteMovieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class FavouriteMovieResourceIntTest {

    private static final Boolean DEFAULT_LIKED = false;
    private static final Boolean UPDATED_LIKED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FavouriteMovieRepository favouriteMovieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFavouriteMovieMockMvc;

    private FavouriteMovie favouriteMovie;

    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final FavouriteMovieResource favouriteMovieResource = new FavouriteMovieResource(favouriteMovieRepository);
//        this.restFavouriteMovieMockMvc = MockMvcBuilders.standaloneSetup(favouriteMovieResource)
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
    public static FavouriteMovie createEntity(EntityManager em) {
        FavouriteMovie favouriteMovie = new FavouriteMovie()
            .liked(DEFAULT_LIKED)
            .date(DEFAULT_DATE);
        return favouriteMovie;
    }

    @Before
    public void initTest() {
        favouriteMovie = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteMovie() throws Exception {
        int databaseSizeBeforeCreate = favouriteMovieRepository.findAll().size();

        // Create the FavouriteMovie
        restFavouriteMovieMockMvc.perform(post("/api/favourite-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteMovie)))
            .andExpect(status().isCreated());

        // Validate the FavouriteMovie in the database
        List<FavouriteMovie> favouriteMovieList = favouriteMovieRepository.findAll();
        assertThat(favouriteMovieList).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteMovie testFavouriteMovie = favouriteMovieList.get(favouriteMovieList.size() - 1);
        assertThat(testFavouriteMovie.isLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testFavouriteMovie.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFavouriteMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouriteMovieRepository.findAll().size();

        // Create the FavouriteMovie with an existing ID
        favouriteMovie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouriteMovieMockMvc.perform(post("/api/favourite-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteMovie)))
            .andExpect(status().isBadRequest());

        // Validate the FavouriteMovie in the database
        List<FavouriteMovie> favouriteMovieList = favouriteMovieRepository.findAll();
        assertThat(favouriteMovieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFavouriteMovies() throws Exception {
        // Initialize the database
        favouriteMovieRepository.saveAndFlush(favouriteMovie);

        // Get all the favouriteMovieList
        restFavouriteMovieMockMvc.perform(get("/api/favourite-movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteMovie.getId().intValue())))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getFavouriteMovie() throws Exception {
        // Initialize the database
        favouriteMovieRepository.saveAndFlush(favouriteMovie);

        // Get the favouriteMovie
        restFavouriteMovieMockMvc.perform(get("/api/favourite-movies/{id}", favouriteMovie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteMovie.getId().intValue()))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteMovie() throws Exception {
        // Get the favouriteMovie
        restFavouriteMovieMockMvc.perform(get("/api/favourite-movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteMovie() throws Exception {
        // Initialize the database
        favouriteMovieRepository.saveAndFlush(favouriteMovie);
        int databaseSizeBeforeUpdate = favouriteMovieRepository.findAll().size();

        // Update the favouriteMovie
        FavouriteMovie updatedFavouriteMovie = favouriteMovieRepository.findOne(favouriteMovie.getId());
        updatedFavouriteMovie
            .liked(UPDATED_LIKED)
            .date(UPDATED_DATE);

        restFavouriteMovieMockMvc.perform(put("/api/favourite-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFavouriteMovie)))
            .andExpect(status().isOk());

        // Validate the FavouriteMovie in the database
        List<FavouriteMovie> favouriteMovieList = favouriteMovieRepository.findAll();
        assertThat(favouriteMovieList).hasSize(databaseSizeBeforeUpdate);
        FavouriteMovie testFavouriteMovie = favouriteMovieList.get(favouriteMovieList.size() - 1);
        assertThat(testFavouriteMovie.isLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testFavouriteMovie.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFavouriteMovie() throws Exception {
        int databaseSizeBeforeUpdate = favouriteMovieRepository.findAll().size();

        // Create the FavouriteMovie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFavouriteMovieMockMvc.perform(put("/api/favourite-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteMovie)))
            .andExpect(status().isCreated());

        // Validate the FavouriteMovie in the database
        List<FavouriteMovie> favouriteMovieList = favouriteMovieRepository.findAll();
        assertThat(favouriteMovieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFavouriteMovie() throws Exception {
        // Initialize the database
        favouriteMovieRepository.saveAndFlush(favouriteMovie);
        int databaseSizeBeforeDelete = favouriteMovieRepository.findAll().size();

        // Get the favouriteMovie
        restFavouriteMovieMockMvc.perform(delete("/api/favourite-movies/{id}", favouriteMovie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FavouriteMovie> favouriteMovieList = favouriteMovieRepository.findAll();
        assertThat(favouriteMovieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteMovie.class);
        FavouriteMovie favouriteMovie1 = new FavouriteMovie();
        favouriteMovie1.setId(1L);
        FavouriteMovie favouriteMovie2 = new FavouriteMovie();
        favouriteMovie2.setId(favouriteMovie1.getId());
        assertThat(favouriteMovie1).isEqualTo(favouriteMovie2);
        favouriteMovie2.setId(2L);
        assertThat(favouriteMovie1).isNotEqualTo(favouriteMovie2);
        favouriteMovie1.setId(null);
        assertThat(favouriteMovie1).isNotEqualTo(favouriteMovie2);
    }
}
