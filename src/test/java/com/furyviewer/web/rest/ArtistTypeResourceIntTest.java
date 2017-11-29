package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.ArtistType;
import com.furyviewer.repository.ArtistTypeRepository;
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

import com.furyviewer.domain.enumeration.ArtistTypeEnum;
/**
 * Test class for the ArtistTypeResource REST controller.
 *
 * @see ArtistTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class ArtistTypeResourceIntTest {

    private static final ArtistTypeEnum DEFAULT_NAME = ArtistTypeEnum.MAIN_ACTOR;
    private static final ArtistTypeEnum UPDATED_NAME = ArtistTypeEnum.SECONDARY_ACTOR;

    @Autowired
    private ArtistTypeRepository artistTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArtistTypeMockMvc;

    private ArtistType artistType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArtistTypeResource artistTypeResource = new ArtistTypeResource(artistTypeRepository);
        this.restArtistTypeMockMvc = MockMvcBuilders.standaloneSetup(artistTypeResource)
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
    public static ArtistType createEntity(EntityManager em) {
        ArtistType artistType = new ArtistType()
            .name(DEFAULT_NAME);
        return artistType;
    }

    @Before
    public void initTest() {
        artistType = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtistType() throws Exception {
        int databaseSizeBeforeCreate = artistTypeRepository.findAll().size();

        // Create the ArtistType
        restArtistTypeMockMvc.perform(post("/api/artist-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artistType)))
            .andExpect(status().isCreated());

        // Validate the ArtistType in the database
        List<ArtistType> artistTypeList = artistTypeRepository.findAll();
        assertThat(artistTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ArtistType testArtistType = artistTypeList.get(artistTypeList.size() - 1);
        assertThat(testArtistType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createArtistTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = artistTypeRepository.findAll().size();

        // Create the ArtistType with an existing ID
        artistType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistTypeMockMvc.perform(post("/api/artist-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artistType)))
            .andExpect(status().isBadRequest());

        // Validate the ArtistType in the database
        List<ArtistType> artistTypeList = artistTypeRepository.findAll();
        assertThat(artistTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArtistTypes() throws Exception {
        // Initialize the database
        artistTypeRepository.saveAndFlush(artistType);

        // Get all the artistTypeList
        restArtistTypeMockMvc.perform(get("/api/artist-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artistType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getArtistType() throws Exception {
        // Initialize the database
        artistTypeRepository.saveAndFlush(artistType);

        // Get the artistType
        restArtistTypeMockMvc.perform(get("/api/artist-types/{id}", artistType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artistType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtistType() throws Exception {
        // Get the artistType
        restArtistTypeMockMvc.perform(get("/api/artist-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtistType() throws Exception {
        // Initialize the database
        artistTypeRepository.saveAndFlush(artistType);
        int databaseSizeBeforeUpdate = artistTypeRepository.findAll().size();

        // Update the artistType
        ArtistType updatedArtistType = artistTypeRepository.findOne(artistType.getId());
        updatedArtistType
            .name(UPDATED_NAME);

        restArtistTypeMockMvc.perform(put("/api/artist-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArtistType)))
            .andExpect(status().isOk());

        // Validate the ArtistType in the database
        List<ArtistType> artistTypeList = artistTypeRepository.findAll();
        assertThat(artistTypeList).hasSize(databaseSizeBeforeUpdate);
        ArtistType testArtistType = artistTypeList.get(artistTypeList.size() - 1);
        assertThat(testArtistType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingArtistType() throws Exception {
        int databaseSizeBeforeUpdate = artistTypeRepository.findAll().size();

        // Create the ArtistType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArtistTypeMockMvc.perform(put("/api/artist-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(artistType)))
            .andExpect(status().isCreated());

        // Validate the ArtistType in the database
        List<ArtistType> artistTypeList = artistTypeRepository.findAll();
        assertThat(artistTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArtistType() throws Exception {
        // Initialize the database
        artistTypeRepository.saveAndFlush(artistType);
        int databaseSizeBeforeDelete = artistTypeRepository.findAll().size();

        // Get the artistType
        restArtistTypeMockMvc.perform(delete("/api/artist-types/{id}", artistType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ArtistType> artistTypeList = artistTypeRepository.findAll();
        assertThat(artistTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArtistType.class);
        ArtistType artistType1 = new ArtistType();
        artistType1.setId(1L);
        ArtistType artistType2 = new ArtistType();
        artistType2.setId(artistType1.getId());
        assertThat(artistType1).isEqualTo(artistType2);
        artistType2.setId(2L);
        assertThat(artistType1).isNotEqualTo(artistType2);
        artistType1.setId(null);
        assertThat(artistType1).isNotEqualTo(artistType2);
    }
}
