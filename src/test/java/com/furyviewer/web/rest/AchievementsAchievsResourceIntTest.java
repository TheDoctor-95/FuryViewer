package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.AchievementsAchievs;
import com.furyviewer.repository.AchievementsAchievsRepository;
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
 * Test class for the AchievementsAchievsResource REST controller.
 *
 * @see AchievementsAchievsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class AchievementsAchievsResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AchievementsAchievsRepository achievementsAchievsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAchievementsAchievsMockMvc;

    private AchievementsAchievs achievementsAchievs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AchievementsAchievsResource achievementsAchievsResource = new AchievementsAchievsResource(achievementsAchievsRepository);
        this.restAchievementsAchievsMockMvc = MockMvcBuilders.standaloneSetup(achievementsAchievsResource)
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
    public static AchievementsAchievs createEntity(EntityManager em) {
        AchievementsAchievs achievementsAchievs = new AchievementsAchievs()
            .date(DEFAULT_DATE);
        return achievementsAchievs;
    }

    @Before
    public void initTest() {
        achievementsAchievs = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievementsAchievs() throws Exception {
        int databaseSizeBeforeCreate = achievementsAchievsRepository.findAll().size();

        // Create the AchievementsAchievs
        restAchievementsAchievsMockMvc.perform(post("/api/achievements-achievs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementsAchievs)))
            .andExpect(status().isCreated());

        // Validate the AchievementsAchievs in the database
        List<AchievementsAchievs> achievementsAchievsList = achievementsAchievsRepository.findAll();
        assertThat(achievementsAchievsList).hasSize(databaseSizeBeforeCreate + 1);
        AchievementsAchievs testAchievementsAchievs = achievementsAchievsList.get(achievementsAchievsList.size() - 1);
        assertThat(testAchievementsAchievs.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAchievementsAchievsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementsAchievsRepository.findAll().size();

        // Create the AchievementsAchievs with an existing ID
        achievementsAchievs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementsAchievsMockMvc.perform(post("/api/achievements-achievs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementsAchievs)))
            .andExpect(status().isBadRequest());

        // Validate the AchievementsAchievs in the database
        List<AchievementsAchievs> achievementsAchievsList = achievementsAchievsRepository.findAll();
        assertThat(achievementsAchievsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAchievementsAchievs() throws Exception {
        // Initialize the database
        achievementsAchievsRepository.saveAndFlush(achievementsAchievs);

        // Get all the achievementsAchievsList
        restAchievementsAchievsMockMvc.perform(get("/api/achievements-achievs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievementsAchievs.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getAchievementsAchievs() throws Exception {
        // Initialize the database
        achievementsAchievsRepository.saveAndFlush(achievementsAchievs);

        // Get the achievementsAchievs
        restAchievementsAchievsMockMvc.perform(get("/api/achievements-achievs/{id}", achievementsAchievs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achievementsAchievs.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingAchievementsAchievs() throws Exception {
        // Get the achievementsAchievs
        restAchievementsAchievsMockMvc.perform(get("/api/achievements-achievs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievementsAchievs() throws Exception {
        // Initialize the database
        achievementsAchievsRepository.saveAndFlush(achievementsAchievs);
        int databaseSizeBeforeUpdate = achievementsAchievsRepository.findAll().size();

        // Update the achievementsAchievs
        AchievementsAchievs updatedAchievementsAchievs = achievementsAchievsRepository.findOne(achievementsAchievs.getId());
        updatedAchievementsAchievs
            .date(UPDATED_DATE);

        restAchievementsAchievsMockMvc.perform(put("/api/achievements-achievs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAchievementsAchievs)))
            .andExpect(status().isOk());

        // Validate the AchievementsAchievs in the database
        List<AchievementsAchievs> achievementsAchievsList = achievementsAchievsRepository.findAll();
        assertThat(achievementsAchievsList).hasSize(databaseSizeBeforeUpdate);
        AchievementsAchievs testAchievementsAchievs = achievementsAchievsList.get(achievementsAchievsList.size() - 1);
        assertThat(testAchievementsAchievs.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievementsAchievs() throws Exception {
        int databaseSizeBeforeUpdate = achievementsAchievsRepository.findAll().size();

        // Create the AchievementsAchievs

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAchievementsAchievsMockMvc.perform(put("/api/achievements-achievs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementsAchievs)))
            .andExpect(status().isCreated());

        // Validate the AchievementsAchievs in the database
        List<AchievementsAchievs> achievementsAchievsList = achievementsAchievsRepository.findAll();
        assertThat(achievementsAchievsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAchievementsAchievs() throws Exception {
        // Initialize the database
        achievementsAchievsRepository.saveAndFlush(achievementsAchievs);
        int databaseSizeBeforeDelete = achievementsAchievsRepository.findAll().size();

        // Get the achievementsAchievs
        restAchievementsAchievsMockMvc.perform(delete("/api/achievements-achievs/{id}", achievementsAchievs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AchievementsAchievs> achievementsAchievsList = achievementsAchievsRepository.findAll();
        assertThat(achievementsAchievsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementsAchievs.class);
        AchievementsAchievs achievementsAchievs1 = new AchievementsAchievs();
        achievementsAchievs1.setId(1L);
        AchievementsAchievs achievementsAchievs2 = new AchievementsAchievs();
        achievementsAchievs2.setId(achievementsAchievs1.getId());
        assertThat(achievementsAchievs1).isEqualTo(achievementsAchievs2);
        achievementsAchievs2.setId(2L);
        assertThat(achievementsAchievs1).isNotEqualTo(achievementsAchievs2);
        achievementsAchievs1.setId(null);
        assertThat(achievementsAchievs1).isNotEqualTo(achievementsAchievs2);
    }
}
