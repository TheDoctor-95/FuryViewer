package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.FavouriteArtist;
import com.furyviewer.repository.FavouriteArtistRepository;
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
 * Test class for the FavouriteArtistResource REST controller.
 *
 * @see FavouriteArtistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class FavouriteArtistResourceIntTest {

    private static final Boolean DEFAULT_LIKED = false;
    private static final Boolean UPDATED_LIKED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FavouriteArtistRepository favouriteArtistRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFavouriteArtistMockMvc;

    private FavouriteArtist favouriteArtist;

    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        final FavouriteArtistResource favouriteArtistResource = new FavouriteArtistResource(favouriteArtistRepository);
//        this.restFavouriteArtistMockMvc = MockMvcBuilders.standaloneSetup(favouriteArtistResource)
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
    public static FavouriteArtist createEntity(EntityManager em) {
        FavouriteArtist favouriteArtist = new FavouriteArtist()
            .liked(DEFAULT_LIKED)
            .date(DEFAULT_DATE);
        return favouriteArtist;
    }

    @Before
    public void initTest() {
        favouriteArtist = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavouriteArtist() throws Exception {
        int databaseSizeBeforeCreate = favouriteArtistRepository.findAll().size();

        // Create the FavouriteArtist
        restFavouriteArtistMockMvc.perform(post("/api/favourite-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteArtist)))
            .andExpect(status().isCreated());

        // Validate the FavouriteArtist in the database
        List<FavouriteArtist> favouriteArtistList = favouriteArtistRepository.findAll();
        assertThat(favouriteArtistList).hasSize(databaseSizeBeforeCreate + 1);
        FavouriteArtist testFavouriteArtist = favouriteArtistList.get(favouriteArtistList.size() - 1);
        assertThat(testFavouriteArtist.isLiked()).isEqualTo(DEFAULT_LIKED);
        assertThat(testFavouriteArtist.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFavouriteArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouriteArtistRepository.findAll().size();

        // Create the FavouriteArtist with an existing ID
        favouriteArtist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouriteArtistMockMvc.perform(post("/api/favourite-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteArtist)))
            .andExpect(status().isBadRequest());

        // Validate the FavouriteArtist in the database
        List<FavouriteArtist> favouriteArtistList = favouriteArtistRepository.findAll();
        assertThat(favouriteArtistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFavouriteArtists() throws Exception {
        // Initialize the database
        favouriteArtistRepository.saveAndFlush(favouriteArtist);

        // Get all the favouriteArtistList
        restFavouriteArtistMockMvc.perform(get("/api/favourite-artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favouriteArtist.getId().intValue())))
            .andExpect(jsonPath("$.[*].liked").value(hasItem(DEFAULT_LIKED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getFavouriteArtist() throws Exception {
        // Initialize the database
        favouriteArtistRepository.saveAndFlush(favouriteArtist);

        // Get the favouriteArtist
        restFavouriteArtistMockMvc.perform(get("/api/favourite-artists/{id}", favouriteArtist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favouriteArtist.getId().intValue()))
            .andExpect(jsonPath("$.liked").value(DEFAULT_LIKED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFavouriteArtist() throws Exception {
        // Get the favouriteArtist
        restFavouriteArtistMockMvc.perform(get("/api/favourite-artists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavouriteArtist() throws Exception {
        // Initialize the database
        favouriteArtistRepository.saveAndFlush(favouriteArtist);
        int databaseSizeBeforeUpdate = favouriteArtistRepository.findAll().size();

        // Update the favouriteArtist
        FavouriteArtist updatedFavouriteArtist = favouriteArtistRepository.findOne(favouriteArtist.getId());
        updatedFavouriteArtist
            .liked(UPDATED_LIKED)
            .date(UPDATED_DATE);

        restFavouriteArtistMockMvc.perform(put("/api/favourite-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFavouriteArtist)))
            .andExpect(status().isOk());

        // Validate the FavouriteArtist in the database
        List<FavouriteArtist> favouriteArtistList = favouriteArtistRepository.findAll();
        assertThat(favouriteArtistList).hasSize(databaseSizeBeforeUpdate);
        FavouriteArtist testFavouriteArtist = favouriteArtistList.get(favouriteArtistList.size() - 1);
        assertThat(testFavouriteArtist.isLiked()).isEqualTo(UPDATED_LIKED);
        assertThat(testFavouriteArtist.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFavouriteArtist() throws Exception {
        int databaseSizeBeforeUpdate = favouriteArtistRepository.findAll().size();

        // Create the FavouriteArtist

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFavouriteArtistMockMvc.perform(put("/api/favourite-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favouriteArtist)))
            .andExpect(status().isCreated());

        // Validate the FavouriteArtist in the database
        List<FavouriteArtist> favouriteArtistList = favouriteArtistRepository.findAll();
        assertThat(favouriteArtistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFavouriteArtist() throws Exception {
        // Initialize the database
        favouriteArtistRepository.saveAndFlush(favouriteArtist);
        int databaseSizeBeforeDelete = favouriteArtistRepository.findAll().size();

        // Get the favouriteArtist
        restFavouriteArtistMockMvc.perform(delete("/api/favourite-artists/{id}", favouriteArtist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FavouriteArtist> favouriteArtistList = favouriteArtistRepository.findAll();
        assertThat(favouriteArtistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouriteArtist.class);
        FavouriteArtist favouriteArtist1 = new FavouriteArtist();
        favouriteArtist1.setId(1L);
        FavouriteArtist favouriteArtist2 = new FavouriteArtist();
        favouriteArtist2.setId(favouriteArtist1.getId());
        assertThat(favouriteArtist1).isEqualTo(favouriteArtist2);
        favouriteArtist2.setId(2L);
        assertThat(favouriteArtist1).isNotEqualTo(favouriteArtist2);
        favouriteArtist1.setId(null);
        assertThat(favouriteArtist1).isNotEqualTo(favouriteArtist2);
    }
}
