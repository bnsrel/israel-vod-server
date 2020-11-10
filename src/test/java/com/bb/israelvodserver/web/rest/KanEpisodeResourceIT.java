package com.bb.israelvodserver.web.rest;

import com.bb.israelvodserver.IsraelVodServerApp;
import com.bb.israelvodserver.domain.KanEpisode;
import com.bb.israelvodserver.repository.KanEpisodeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KanEpisodeResource} REST controller.
 */
@SpringBootTest(classes = IsraelVodServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KanEpisodeResourceIT {

    private static final Integer DEFAULT_EPISODE_ID = 1;
    private static final Integer UPDATED_EPISODE_ID = 2;

    private static final Integer DEFAULT_SERIES_ID = 1;
    private static final Integer UPDATED_SERIES_ID = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_SRC = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_SRC = "BBBBBBBBBB";

    @Autowired
    private KanEpisodeRepository kanEpisodeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKanEpisodeMockMvc;

    private KanEpisode kanEpisode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanEpisode createEntity(EntityManager em) {
        KanEpisode kanEpisode = new KanEpisode()
            .episodeId(DEFAULT_EPISODE_ID)
            .seriesId(DEFAULT_SERIES_ID)
            .title(DEFAULT_TITLE)
            .text(DEFAULT_TEXT)
            .img(DEFAULT_IMG)
            .videoSrc(DEFAULT_VIDEO_SRC);
        return kanEpisode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanEpisode createUpdatedEntity(EntityManager em) {
        KanEpisode kanEpisode = new KanEpisode()
            .episodeId(UPDATED_EPISODE_ID)
            .seriesId(UPDATED_SERIES_ID)
            .title(UPDATED_TITLE)
            .text(UPDATED_TEXT)
            .img(UPDATED_IMG)
            .videoSrc(UPDATED_VIDEO_SRC);
        return kanEpisode;
    }

    @BeforeEach
    public void initTest() {
        kanEpisode = createEntity(em);
    }

    @Test
    @Transactional
    public void createKanEpisode() throws Exception {
        int databaseSizeBeforeCreate = kanEpisodeRepository.findAll().size();
        // Create the KanEpisode
        restKanEpisodeMockMvc.perform(post("/api/kan-episodes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanEpisode)))
            .andExpect(status().isCreated());

        // Validate the KanEpisode in the database
        List<KanEpisode> kanEpisodeList = kanEpisodeRepository.findAll();
        assertThat(kanEpisodeList).hasSize(databaseSizeBeforeCreate + 1);
        KanEpisode testKanEpisode = kanEpisodeList.get(kanEpisodeList.size() - 1);
        assertThat(testKanEpisode.getEpisodeId()).isEqualTo(DEFAULT_EPISODE_ID);
        assertThat(testKanEpisode.getSeriesId()).isEqualTo(DEFAULT_SERIES_ID);
        assertThat(testKanEpisode.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testKanEpisode.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testKanEpisode.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testKanEpisode.getVideoSrc()).isEqualTo(DEFAULT_VIDEO_SRC);
    }

    @Test
    @Transactional
    public void createKanEpisodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kanEpisodeRepository.findAll().size();

        // Create the KanEpisode with an existing ID
        kanEpisode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKanEpisodeMockMvc.perform(post("/api/kan-episodes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanEpisode)))
            .andExpect(status().isBadRequest());

        // Validate the KanEpisode in the database
        List<KanEpisode> kanEpisodeList = kanEpisodeRepository.findAll();
        assertThat(kanEpisodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKanEpisodes() throws Exception {
        // Initialize the database
        kanEpisodeRepository.saveAndFlush(kanEpisode);

        // Get all the kanEpisodeList
        restKanEpisodeMockMvc.perform(get("/api/kan-episodes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kanEpisode.getId().intValue())))
            .andExpect(jsonPath("$.[*].episodeId").value(hasItem(DEFAULT_EPISODE_ID)))
            .andExpect(jsonPath("$.[*].seriesId").value(hasItem(DEFAULT_SERIES_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG)))
            .andExpect(jsonPath("$.[*].videoSrc").value(hasItem(DEFAULT_VIDEO_SRC)));
    }
    
    @Test
    @Transactional
    public void getKanEpisode() throws Exception {
        // Initialize the database
        kanEpisodeRepository.saveAndFlush(kanEpisode);

        // Get the kanEpisode
        restKanEpisodeMockMvc.perform(get("/api/kan-episodes/{id}", kanEpisode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kanEpisode.getId().intValue()))
            .andExpect(jsonPath("$.episodeId").value(DEFAULT_EPISODE_ID))
            .andExpect(jsonPath("$.seriesId").value(DEFAULT_SERIES_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG))
            .andExpect(jsonPath("$.videoSrc").value(DEFAULT_VIDEO_SRC));
    }
    @Test
    @Transactional
    public void getNonExistingKanEpisode() throws Exception {
        // Get the kanEpisode
        restKanEpisodeMockMvc.perform(get("/api/kan-episodes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanEpisode() throws Exception {
        // Initialize the database
        kanEpisodeRepository.saveAndFlush(kanEpisode);

        int databaseSizeBeforeUpdate = kanEpisodeRepository.findAll().size();

        // Update the kanEpisode
        KanEpisode updatedKanEpisode = kanEpisodeRepository.findById(kanEpisode.getId()).get();
        // Disconnect from session so that the updates on updatedKanEpisode are not directly saved in db
        em.detach(updatedKanEpisode);
        updatedKanEpisode
            .episodeId(UPDATED_EPISODE_ID)
            .seriesId(UPDATED_SERIES_ID)
            .title(UPDATED_TITLE)
            .text(UPDATED_TEXT)
            .img(UPDATED_IMG)
            .videoSrc(UPDATED_VIDEO_SRC);

        restKanEpisodeMockMvc.perform(put("/api/kan-episodes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKanEpisode)))
            .andExpect(status().isOk());

        // Validate the KanEpisode in the database
        List<KanEpisode> kanEpisodeList = kanEpisodeRepository.findAll();
        assertThat(kanEpisodeList).hasSize(databaseSizeBeforeUpdate);
        KanEpisode testKanEpisode = kanEpisodeList.get(kanEpisodeList.size() - 1);
        assertThat(testKanEpisode.getEpisodeId()).isEqualTo(UPDATED_EPISODE_ID);
        assertThat(testKanEpisode.getSeriesId()).isEqualTo(UPDATED_SERIES_ID);
        assertThat(testKanEpisode.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testKanEpisode.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testKanEpisode.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testKanEpisode.getVideoSrc()).isEqualTo(UPDATED_VIDEO_SRC);
    }

    @Test
    @Transactional
    public void updateNonExistingKanEpisode() throws Exception {
        int databaseSizeBeforeUpdate = kanEpisodeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKanEpisodeMockMvc.perform(put("/api/kan-episodes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanEpisode)))
            .andExpect(status().isBadRequest());

        // Validate the KanEpisode in the database
        List<KanEpisode> kanEpisodeList = kanEpisodeRepository.findAll();
        assertThat(kanEpisodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKanEpisode() throws Exception {
        // Initialize the database
        kanEpisodeRepository.saveAndFlush(kanEpisode);

        int databaseSizeBeforeDelete = kanEpisodeRepository.findAll().size();

        // Delete the kanEpisode
        restKanEpisodeMockMvc.perform(delete("/api/kan-episodes/{id}", kanEpisode.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KanEpisode> kanEpisodeList = kanEpisodeRepository.findAll();
        assertThat(kanEpisodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
