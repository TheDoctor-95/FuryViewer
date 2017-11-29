package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.HatredArtist;
import com.furyviewer.repository.HatredArtistRepository;
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
 * Test class for the HatredArtistResource REST controller.
 *
 * @see HatredArtistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class HatredArtistResourceIntTest {

    private static final Boolean DEFAULT_HATED = false;
    private static final Boolean UPDATED_HATED = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private HatredArtistRepository hatredArtistRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHatredArtistMockMvc;

    private HatredArtist hatredArtist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HatredArtistResource hatredArtistResource = new HatredArtistResource(hatredArtistRepository);
        this.restHatredArtistMockMvc = MockMvcBuilders.standaloneSetup(hatredArtistResource)
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
    public static HatredArtist createEntity(EntityManager em) {
        HatredArtist hatredArtist = new HatredArtist()
            .hated(DEFAULT_HATED)
            .date(DEFAULT_DATE);
        return hatredArtist;
    }

    @Before
    public void initTest() {
        hatredArtist = createEntity(em);
    }

    @Test
    @Transactional
    public void createHatredArtist() throws Exception {
        int databaseSizeBeforeCreate = hatredArtistRepository.findAll().size();

        // Create the HatredArtist
        restHatredArtistMockMvc.perform(post("/api/hatred-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredArtist)))
            .andExpect(status().isCreated());

        // Validate the HatredArtist in the database
        List<HatredArtist> hatredArtistList = hatredArtistRepository.findAll();
        assertThat(hatredArtistList).hasSize(databaseSizeBeforeCreate + 1);
        HatredArtist testHatredArtist = hatredArtistList.get(hatredArtistList.size() - 1);
        assertThat(testHatredArtist.isHated()).isEqualTo(DEFAULT_HATED);
        assertThat(testHatredArtist.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createHatredArtistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hatredArtistRepository.findAll().size();

        // Create the HatredArtist with an existing ID
        hatredArtist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHatredArtistMockMvc.perform(post("/api/hatred-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredArtist)))
            .andExpect(status().isBadRequest());

        // Validate the HatredArtist in the database
        List<HatredArtist> hatredArtistList = hatredArtistRepository.findAll();
        assertThat(hatredArtistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHatredArtists() throws Exception {
        // Initialize the database
        hatredArtistRepository.saveAndFlush(hatredArtist);

        // Get all the hatredArtistList
        restHatredArtistMockMvc.perform(get("/api/hatred-artists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hatredArtist.getId().intValue())))
            .andExpect(jsonPath("$.[*].hated").value(hasItem(DEFAULT_HATED.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getHatredArtist() throws Exception {
        // Initialize the database
        hatredArtistRepository.saveAndFlush(hatredArtist);

        // Get the hatredArtist
        restHatredArtistMockMvc.perform(get("/api/hatred-artists/{id}", hatredArtist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hatredArtist.getId().intValue()))
            .andExpect(jsonPath("$.hated").value(DEFAULT_HATED.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingHatredArtist() throws Exception {
        // Get the hatredArtist
        restHatredArtistMockMvc.perform(get("/api/hatred-artists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHatredArtist() throws Exception {
        // Initialize the database
        hatredArtistRepository.saveAndFlush(hatredArtist);
        int databaseSizeBeforeUpdate = hatredArtistRepository.findAll().size();

        // Update the hatredArtist
        HatredArtist updatedHatredArtist = hatredArtistRepository.findOne(hatredArtist.getId());
        updatedHatredArtist
            .hated(UPDATED_HATED)
            .date(UPDATED_DATE);

        restHatredArtistMockMvc.perform(put("/api/hatred-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHatredArtist)))
            .andExpect(status().isOk());

        // Validate the HatredArtist in the database
        List<HatredArtist> hatredArtistList = hatredArtistRepository.findAll();
        assertThat(hatredArtistList).hasSize(databaseSizeBeforeUpdate);
        HatredArtist testHatredArtist = hatredArtistList.get(hatredArtistList.size() - 1);
        assertThat(testHatredArtist.isHated()).isEqualTo(UPDATED_HATED);
        assertThat(testHatredArtist.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHatredArtist() throws Exception {
        int databaseSizeBeforeUpdate = hatredArtistRepository.findAll().size();

        // Create the HatredArtist

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHatredArtistMockMvc.perform(put("/api/hatred-artists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hatredArtist)))
            .andExpect(status().isCreated());

        // Validate the HatredArtist in the database
        List<HatredArtist> hatredArtistList = hatredArtistRepository.findAll();
        assertThat(hatredArtistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHatredArtist() throws Exception {
        // Initialize the database
        hatredArtistRepository.saveAndFlush(hatredArtist);
        int databaseSizeBeforeDelete = hatredArtistRepository.findAll().size();

        // Get the hatredArtist
        restHatredArtistMockMvc.perform(delete("/api/hatred-artists/{id}", hatredArtist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HatredArtist> hatredArtistList = hatredArtistRepository.findAll();
        assertThat(hatredArtistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HatredArtist.class);
        HatredArtist hatredArtist1 = new HatredArtist();
        hatredArtist1.setId(1L);
        HatredArtist hatredArtist2 = new HatredArtist();
        hatredArtist2.setId(hatredArtist1.getId());
        assertThat(hatredArtist1).isEqualTo(hatredArtist2);
        hatredArtist2.setId(2L);
        assertThat(hatredArtist1).isNotEqualTo(hatredArtist2);
        hatredArtist1.setId(null);
        assertThat(hatredArtist1).isNotEqualTo(hatredArtist2);
    }
}
