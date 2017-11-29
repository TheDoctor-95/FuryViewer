package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.Social;
import com.furyviewer.repository.SocialRepository;
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
import java.util.List;

import static com.furyviewer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SocialResource REST controller.
 *
 * @see SocialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class SocialResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private SocialRepository socialRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocialMockMvc;

    private Social social;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocialResource socialResource = new SocialResource(socialRepository);
        this.restSocialMockMvc = MockMvcBuilders.standaloneSetup(socialResource)
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
    public static Social createEntity(EntityManager em) {
        Social social = new Social()
            .url(DEFAULT_URL);
        return social;
    }

    @Before
    public void initTest() {
        social = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocial() throws Exception {
        int databaseSizeBeforeCreate = socialRepository.findAll().size();

        // Create the Social
        restSocialMockMvc.perform(post("/api/socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(social)))
            .andExpect(status().isCreated());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeCreate + 1);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createSocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialRepository.findAll().size();

        // Create the Social with an existing ID
        social.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMockMvc.perform(post("/api/socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(social)))
            .andExpect(status().isBadRequest());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocials() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        // Get all the socialList
        restSocialMockMvc.perform(get("/api/socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(social.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);

        // Get the social
        restSocialMockMvc.perform(get("/api/socials/{id}", social.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(social.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocial() throws Exception {
        // Get the social
        restSocialMockMvc.perform(get("/api/socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();

        // Update the social
        Social updatedSocial = socialRepository.findOne(social.getId());
        updatedSocial
            .url(UPDATED_URL);

        restSocialMockMvc.perform(put("/api/socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocial)))
            .andExpect(status().isOk());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate);
        Social testSocial = socialList.get(socialList.size() - 1);
        assertThat(testSocial.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingSocial() throws Exception {
        int databaseSizeBeforeUpdate = socialRepository.findAll().size();

        // Create the Social

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialMockMvc.perform(put("/api/socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(social)))
            .andExpect(status().isCreated());

        // Validate the Social in the database
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocial() throws Exception {
        // Initialize the database
        socialRepository.saveAndFlush(social);
        int databaseSizeBeforeDelete = socialRepository.findAll().size();

        // Get the social
        restSocialMockMvc.perform(delete("/api/socials/{id}", social.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Social> socialList = socialRepository.findAll();
        assertThat(socialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Social.class);
        Social social1 = new Social();
        social1.setId(1L);
        Social social2 = new Social();
        social2.setId(social1.getId());
        assertThat(social1).isEqualTo(social2);
        social2.setId(2L);
        assertThat(social1).isNotEqualTo(social2);
        social1.setId(null);
        assertThat(social1).isNotEqualTo(social2);
    }
}
