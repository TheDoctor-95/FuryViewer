package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.HatredMovie;
import com.furyviewer.repository.HatredMovieRepository;
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
 * Test class for the HatredMovieResource REST controller.
 *
 * @see HatredMovieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class HatredMovieResourceIntTest {

    private static final Boolean DEFAULT_HATED = false;
    private static final Boolean UPDATED_HATED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HatredMovieRepository hatredMovieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHatredMovieMockMvc;

    private HatredMovie hatredMovie;

    @Before
   /* public void setup() {
        MockitoAnnotations.initMocks(this);
        final HatredMovieResource hatredMovieResource = new HatredMovieResource(hatredMovieRepository);
        this.restHatredMovieMockMvc = MockMvcBuilders.standaloneSetup(hatredMovieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }*/

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HatredMovie createEntity(EntityManager em) {
        HatredMovie hatredMovie = new HatredMovie()
            .hated(DEFAULT_HATED)
            .date(DEFAULT_DATE);
        return hatredMovie;
    }

    @Before
    public void initTest() {
        hatredMovie = createEntity(em);
    }

    @Test
    @Transactional
    public void createHatredMovie() throws Exception {
        int databaseSizeBeforeCreate = hatredMovieRepository.findAll().size();

        // Create the HatredMovie
        restHatredMovieMockMvc.perform(post("/api/hatred-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredMovie)))
            .andExpect(status().isCreated());

        // Validate the HatredMovie in the database
        List<HatredMovie> hatredMovieList = hatredMovieRepository.findAll();
        assertThat(hatredMovieList).hasSize(databaseSizeBeforeCreate + 1);
        HatredMovie testHatredMovie = hatredMovieList.get(hatredMovieList.size() - 1);
        assertThat(testHatredMovie.isHated()).isEqualTo(DEFAULT_HATED);
        assertThat(testHatredMovie.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createHatredMovieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hatredMovieRepository.findAll().size();

        // Create the HatredMovie with an existing ID
        hatredMovie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHatredMovieMockMvc.perform(post("/api/hatred-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredMovie)))
            .andExpect(status().isBadRequest());

        // Validate the HatredMovie in the database
        List<HatredMovie> hatredMovieList = hatredMovieRepository.findAll();
        assertThat(hatredMovieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHatredMovies() throws Exception {
        // Initialize the database
        hatredMovieRepository.saveAndFlush(hatredMovie);

        // Get all the hatredMovieList
        restHatredMovieMockMvc.perform(get("/api/hatred-movies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hatredMovie.getId().intValue())))
            .andExpect(jsonPath("$.[*].hated").value(hasItem(DEFAULT_HATED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getHatredMovie() throws Exception {
        // Initialize the database
        hatredMovieRepository.saveAndFlush(hatredMovie);

        // Get the hatredMovie
        restHatredMovieMockMvc.perform(get("/api/hatred-movies/{id}", hatredMovie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hatredMovie.getId().intValue()))
            .andExpect(jsonPath("$.hated").value(DEFAULT_HATED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingHatredMovie() throws Exception {
        // Get the hatredMovie
        restHatredMovieMockMvc.perform(get("/api/hatred-movies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHatredMovie() throws Exception {
        // Initialize the database
        hatredMovieRepository.saveAndFlush(hatredMovie);
        int databaseSizeBeforeUpdate = hatredMovieRepository.findAll().size();

        // Update the hatredMovie
        HatredMovie updatedHatredMovie = hatredMovieRepository.findOne(hatredMovie.getId());
        updatedHatredMovie
            .hated(UPDATED_HATED)
            .date(UPDATED_DATE);

        restHatredMovieMockMvc.perform(put("/api/hatred-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHatredMovie)))
            .andExpect(status().isOk());

        // Validate the HatredMovie in the database
        List<HatredMovie> hatredMovieList = hatredMovieRepository.findAll();
        assertThat(hatredMovieList).hasSize(databaseSizeBeforeUpdate);
        HatredMovie testHatredMovie = hatredMovieList.get(hatredMovieList.size() - 1);
        assertThat(testHatredMovie.isHated()).isEqualTo(UPDATED_HATED);
        assertThat(testHatredMovie.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHatredMovie() throws Exception {
        int databaseSizeBeforeUpdate = hatredMovieRepository.findAll().size();

        // Create the HatredMovie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHatredMovieMockMvc.perform(put("/api/hatred-movies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredMovie)))
            .andExpect(status().isCreated());

        // Validate the HatredMovie in the database
        List<HatredMovie> hatredMovieList = hatredMovieRepository.findAll();
        assertThat(hatredMovieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHatredMovie() throws Exception {
        // Initialize the database
        hatredMovieRepository.saveAndFlush(hatredMovie);
        int databaseSizeBeforeDelete = hatredMovieRepository.findAll().size();

        // Get the hatredMovie
        restHatredMovieMockMvc.perform(delete("/api/hatred-movies/{id}", hatredMovie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HatredMovie> hatredMovieList = hatredMovieRepository.findAll();
        assertThat(hatredMovieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HatredMovie.class);
        HatredMovie hatredMovie1 = new HatredMovie();
        hatredMovie1.setId(1L);
        HatredMovie hatredMovie2 = new HatredMovie();
        hatredMovie2.setId(hatredMovie1.getId());
        assertThat(hatredMovie1).isEqualTo(hatredMovie2);
        hatredMovie2.setId(2L);
        assertThat(hatredMovie1).isNotEqualTo(hatredMovie2);
        hatredMovie1.setId(null);
        assertThat(hatredMovie1).isNotEqualTo(hatredMovie2);
    }
}
