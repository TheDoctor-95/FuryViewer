package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.FavouriteSeries;
import com.furyviewer.repository.FavouriteSeriesRepository;
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
 * Test class for the FavouriteSeriesResource REST controller.
 *
 * @see FavouriteSeriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class FavouriteSeriesResourceIntTest {

    private static final Boolean DEFAULT_LIKED = false;
    private static final Boolean UPDATED_LIKED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FavouriteSeriesRepository favouriteSeriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFavouriteSeriesMockMvc;

    private FavouriteSeries favouriteSeries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FavouriteSeriesResource favouriteSeriesResource = new FavouriteSeriesResource(favouriteSeriesRepository);
        this.restFavouriteSeriesMockMvc = MockMvcBuilders.standaloneSetup(favouriteSeriesResource)
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
    public static FavouriteSeries createEntity(EntityManager em) {
        FavouriteSeries favouriteSeries = new FavouriteSeries()
            .liked(DEFAULT_LIKED)
            .date(DEFAULT_DATE);
        return favouriteSeries;
    }

    @Before
    public void initTest() {
        favouriteSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteSeries() throws Exception {
        int databaseSizeBeforeCreate = favouriteSeriesRepository.findAll().size();

        // Create the FavouriteSeries
        restFavouriteSeriesMockMvc.perform(post("/api/favourite-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteSeries)))
            .andExpect(status().isCreated());

        // Validate the FavouriteSeries in the database
        List<FavouriteSeries> favouriteSeriesList = favouriteSeriesRepository.findAll();
        assertThat(favouriteSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteSeries testFavouriteSeries = favouriteSeriesList.get(favouriteSeriesList.size() - 1);
        assertThat(testFavouriteSeries.isLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testFavouriteSeries.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFavouriteSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouriteSeriesRepository.findAll().size();

        // Create the FavouriteSeries with an existing ID
        favouriteSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouriteSeriesMockMvc.perform(post("/api/favourite-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteSeries)))
            .andExpect(status().isBadRequest());

        // Validate the FavouriteSeries in the database
        List<FavouriteSeries> favouriteSeriesList = favouriteSeriesRepository.findAll();
        assertThat(favouriteSeriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFavouriteSeries() throws Exception {
        // Initialize the database
        favouriteSeriesRepository.saveAndFlush(favouriteSeries);

        // Get all the favouriteSeriesList
        restFavouriteSeriesMockMvc.perform(get("/api/favourite-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getFavouriteSeries() throws Exception {
        // Initialize the database
        favouriteSeriesRepository.saveAndFlush(favouriteSeries);

        // Get the favouriteSeries
        restFavouriteSeriesMockMvc.perform(get("/api/favourite-series/{id}", favouriteSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteSeries.getId().intValue()))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteSeries() throws Exception {
        // Get the favouriteSeries
        restFavouriteSeriesMockMvc.perform(get("/api/favourite-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteSeries() throws Exception {
        // Initialize the database
        favouriteSeriesRepository.saveAndFlush(favouriteSeries);
        int databaseSizeBeforeUpdate = favouriteSeriesRepository.findAll().size();

        // Update the favouriteSeries
        FavouriteSeries updatedFavouriteSeries = favouriteSeriesRepository.findOne(favouriteSeries.getId());
        updatedFavouriteSeries
            .liked(UPDATED_LIKED)
            .date(UPDATED_DATE);

        restFavouriteSeriesMockMvc.perform(put("/api/favourite-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFavouriteSeries)))
            .andExpect(status().isOk());

        // Validate the FavouriteSeries in the database
        List<FavouriteSeries> favouriteSeriesList = favouriteSeriesRepository.findAll();
        assertThat(favouriteSeriesList).hasSize(databaseSizeBeforeUpdate);
        FavouriteSeries testFavouriteSeries = favouriteSeriesList.get(favouriteSeriesList.size() - 1);
        assertThat(testFavouriteSeries.isLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testFavouriteSeries.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFavouriteSeries() throws Exception {
        int databaseSizeBeforeUpdate = favouriteSeriesRepository.findAll().size();

        // Create the FavouriteSeries

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFavouriteSeriesMockMvc.perform(put("/api/favourite-series")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteSeries)))
            .andExpect(status().isCreated());

        // Validate the FavouriteSeries in the database
        List<FavouriteSeries> favouriteSeriesList = favouriteSeriesRepository.findAll();
        assertThat(favouriteSeriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFavouriteSeries() throws Exception {
        // Initialize the database
        favouriteSeriesRepository.saveAndFlush(favouriteSeries);
        int databaseSizeBeforeDelete = favouriteSeriesRepository.findAll().size();

        // Get the favouriteSeries
        restFavouriteSeriesMockMvc.perform(delete("/api/favourite-series/{id}", favouriteSeries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FavouriteSeries> favouriteSeriesList = favouriteSeriesRepository.findAll();
        assertThat(favouriteSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteSeries.class);
        FavouriteSeries favouriteSeries1 = new FavouriteSeries();
        favouriteSeries1.setId(1L);
        FavouriteSeries favouriteSeries2 = new FavouriteSeries();
        favouriteSeries2.setId(favouriteSeries1.getId());
        assertThat(favouriteSeries1).isEqualTo(favouriteSeries2);
        favouriteSeries2.setId(2L);
        assertThat(favouriteSeries1).isNotEqualTo(favouriteSeries2);
        favouriteSeries1.setId(null);
        assertThat(favouriteSeries1).isNotEqualTo(favouriteSeries2);
    }
}
