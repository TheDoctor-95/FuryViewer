package com.furyviewer.web.rest;

import com.furyviewer.FuryViewerApp;

import com.furyviewer.domain.ChapterSeen;
import com.furyviewer.repository.ChapterSeenRepository;
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
 * Test class for the ChapterSeenResource REST controller.
 *
 * @see ChapterSeenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuryViewerApp.class)
public class ChapterSeenResourceIntTest {

    private static final Boolean DEFAULT_SEEN = false;
    private static final Boolean UPDATED_SEEN = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ChapterSeenRepository chapterSeenRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChapterSeenMockMvc;

    private ChapterSeen chapterSeen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChapterSeenResource chapterSeenResource = new ChapterSeenResource(chapterSeenRepository);
        this.restChapterSeenMockMvc = MockMvcBuilders.standaloneSetup(chapterSeenResource)
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
    public static ChapterSeen createEntity(EntityManager em) {
        ChapterSeen chapterSeen = new ChapterSeen()
            .seen(DEFAULT_SEEN)
            .date(DEFAULT_DATE);
        return chapterSeen;
    }

    @Before
    public void initTest() {
        chapterSeen = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapterSeen() throws Exception {
        int databaseSizeBeforeCreate = chapterSeenRepository.findAll().size();

        // Create the ChapterSeen
        restChapterSeenMockMvc.perform(post("/api/chapter-seens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapterSeen)))
            .andExpect(status().isCreated());

        // Validate the ChapterSeen in the database
        List<ChapterSeen> chapterSeenList = chapterSeenRepository.findAll();
        assertThat(chapterSeenList).hasSize(databaseSizeBeforeCreate + 1);
        ChapterSeen testChapterSeen = chapterSeenList.get(chapterSeenList.size() - 1);
        assertThat(testChapterSeen.isSeen()).isEqualTo(DEFAULT_SEEN);
        assertThat(testChapterSeen.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createChapterSeenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chapterSeenRepository.findAll().size();

        // Create the ChapterSeen with an existing ID
        chapterSeen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChapterSeenMockMvc.perform(post("/api/chapter-seens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapterSeen)))
            .andExpect(status().isBadRequest());

        // Validate the ChapterSeen in the database
        List<ChapterSeen> chapterSeenList = chapterSeenRepository.findAll();
        assertThat(chapterSeenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChapterSeens() throws Exception {
        // Initialize the database
        chapterSeenRepository.saveAndFlush(chapterSeen);

        // Get all the chapterSeenList
        restChapterSeenMockMvc.perform(get("/api/chapter-seens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapterSeen.getId().intValue())))
            .andExpect(jsonPath("$.[*].seen").value(hasItem(DEFAULT_SEEN.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getChapterSeen() throws Exception {
        // Initialize the database
        chapterSeenRepository.saveAndFlush(chapterSeen);

        // Get the chapterSeen
        restChapterSeenMockMvc.perform(get("/api/chapter-seens/{id}", chapterSeen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapterSeen.getId().intValue()))
            .andExpect(jsonPath("$.seen").value(DEFAULT_SEEN.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingChapterSeen() throws Exception {
        // Get the chapterSeen
        restChapterSeenMockMvc.perform(get("/api/chapter-seens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapterSeen() throws Exception {
        // Initialize the database
        chapterSeenRepository.saveAndFlush(chapterSeen);
        int databaseSizeBeforeUpdate = chapterSeenRepository.findAll().size();

        // Update the chapterSeen
        ChapterSeen updatedChapterSeen = chapterSeenRepository.findOne(chapterSeen.getId());
        updatedChapterSeen
            .seen(UPDATED_SEEN)
            .date(UPDATED_DATE);

        restChapterSeenMockMvc.perform(put("/api/chapter-seens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChapterSeen)))
            .andExpect(status().isOk());

        // Validate the ChapterSeen in the database
        List<ChapterSeen> chapterSeenList = chapterSeenRepository.findAll();
        assertThat(chapterSeenList).hasSize(databaseSizeBeforeUpdate);
        ChapterSeen testChapterSeen = chapterSeenList.get(chapterSeenList.size() - 1);
        assertThat(testChapterSeen.isSeen()).isEqualTo(UPDATED_SEEN);
        assertThat(testChapterSeen.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChapterSeen() throws Exception {
        int databaseSizeBeforeUpdate = chapterSeenRepository.findAll().size();

        // Create the ChapterSeen

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChapterSeenMockMvc.perform(put("/api/chapter-seens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chapterSeen)))
            .andExpect(status().isCreated());

        // Validate the ChapterSeen in the database
        List<ChapterSeen> chapterSeenList = chapterSeenRepository.findAll();
        assertThat(chapterSeenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChapterSeen() throws Exception {
        // Initialize the database
        chapterSeenRepository.saveAndFlush(chapterSeen);
        int databaseSizeBeforeDelete = chapterSeenRepository.findAll().size();

        // Get the chapterSeen
        restChapterSeenMockMvc.perform(delete("/api/chapter-seens/{id}", chapterSeen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChapterSeen> chapterSeenList = chapterSeenRepository.findAll();
        assertThat(chapterSeenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChapterSeen.class);
        ChapterSeen chapterSeen1 = new ChapterSeen();
        chapterSeen1.setId(1L);
        ChapterSeen chapterSeen2 = new ChapterSeen();
        chapterSeen2.setId(chapterSeen1.getId());
        assertThat(chapterSeen1).isEqualTo(chapterSeen2);
        chapterSeen2.setId(2L);
        assertThat(chapterSeen1).isNotEqualTo(chapterSeen2);
        chapterSeen1.setId(null);
        assertThat(chapterSeen1).isNotEqualTo(chapterSeen2);
    }
}
