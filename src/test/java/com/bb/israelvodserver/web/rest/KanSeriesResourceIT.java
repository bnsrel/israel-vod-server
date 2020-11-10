package com.bb.israelvodserver.web.rest;

import com.bb.israelvodserver.IsraelVodServerApp;
import com.bb.israelvodserver.domain.KanSeries;
import com.bb.israelvodserver.repository.KanSeriesRepository;

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
 * Integration tests for the {@link KanSeriesResource} REST controller.
 */
@SpringBootTest(classes = IsraelVodServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KanSeriesResourceIT {

    private static final Integer DEFAULT_SERIES_ID = 1;
    private static final Integer UPDATED_SERIES_ID = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_IMG = "AAAAAAAAAA";
    private static final String UPDATED_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_PAGE = "BBBBBBBBBB";

    @Autowired
    private KanSeriesRepository kanSeriesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKanSeriesMockMvc;

    private KanSeries kanSeries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanSeries createEntity(EntityManager em) {
        KanSeries kanSeries = new KanSeries()
            .seriesId(DEFAULT_SERIES_ID)
            .title(DEFAULT_TITLE)
            .text(DEFAULT_TEXT)
            .img(DEFAULT_IMG)
            .webPage(DEFAULT_WEB_PAGE);
        return kanSeries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KanSeries createUpdatedEntity(EntityManager em) {
        KanSeries kanSeries = new KanSeries()
            .seriesId(UPDATED_SERIES_ID)
            .title(UPDATED_TITLE)
            .text(UPDATED_TEXT)
            .img(UPDATED_IMG)
            .webPage(UPDATED_WEB_PAGE);
        return kanSeries;
    }

    @BeforeEach
    public void initTest() {
        kanSeries = createEntity(em);
    }

    @Test
    @Transactional
    public void createKanSeries() throws Exception {
        int databaseSizeBeforeCreate = kanSeriesRepository.findAll().size();
        // Create the KanSeries
        restKanSeriesMockMvc.perform(post("/api/kan-series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanSeries)))
            .andExpect(status().isCreated());

        // Validate the KanSeries in the database
        List<KanSeries> kanSeriesList = kanSeriesRepository.findAll();
        assertThat(kanSeriesList).hasSize(databaseSizeBeforeCreate + 1);
        KanSeries testKanSeries = kanSeriesList.get(kanSeriesList.size() - 1);
        assertThat(testKanSeries.getSeriesId()).isEqualTo(DEFAULT_SERIES_ID);
        assertThat(testKanSeries.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testKanSeries.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testKanSeries.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testKanSeries.getWebPage()).isEqualTo(DEFAULT_WEB_PAGE);
    }

    @Test
    @Transactional
    public void createKanSeriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kanSeriesRepository.findAll().size();

        // Create the KanSeries with an existing ID
        kanSeries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKanSeriesMockMvc.perform(post("/api/kan-series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanSeries)))
            .andExpect(status().isBadRequest());

        // Validate the KanSeries in the database
        List<KanSeries> kanSeriesList = kanSeriesRepository.findAll();
        assertThat(kanSeriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKanSeries() throws Exception {
        // Initialize the database
        kanSeriesRepository.saveAndFlush(kanSeries);

        // Get all the kanSeriesList
        restKanSeriesMockMvc.perform(get("/api/kan-series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kanSeries.getId().intValue())))
            .andExpect(jsonPath("$.[*].seriesId").value(hasItem(DEFAULT_SERIES_ID)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(DEFAULT_IMG)))
            .andExpect(jsonPath("$.[*].webPage").value(hasItem(DEFAULT_WEB_PAGE)));
    }
    
    @Test
    @Transactional
    public void getKanSeries() throws Exception {
        // Initialize the database
        kanSeriesRepository.saveAndFlush(kanSeries);

        // Get the kanSeries
        restKanSeriesMockMvc.perform(get("/api/kan-series/{id}", kanSeries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kanSeries.getId().intValue()))
            .andExpect(jsonPath("$.seriesId").value(DEFAULT_SERIES_ID))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.img").value(DEFAULT_IMG))
            .andExpect(jsonPath("$.webPage").value(DEFAULT_WEB_PAGE));
    }
    @Test
    @Transactional
    public void getNonExistingKanSeries() throws Exception {
        // Get the kanSeries
        restKanSeriesMockMvc.perform(get("/api/kan-series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKanSeries() throws Exception {
        // Initialize the database
        kanSeriesRepository.saveAndFlush(kanSeries);

        int databaseSizeBeforeUpdate = kanSeriesRepository.findAll().size();

        // Update the kanSeries
        KanSeries updatedKanSeries = kanSeriesRepository.findById(kanSeries.getId()).get();
        // Disconnect from session so that the updates on updatedKanSeries are not directly saved in db
        em.detach(updatedKanSeries);
        updatedKanSeries
            .seriesId(UPDATED_SERIES_ID)
            .title(UPDATED_TITLE)
            .text(UPDATED_TEXT)
            .img(UPDATED_IMG)
            .webPage(UPDATED_WEB_PAGE);

        restKanSeriesMockMvc.perform(put("/api/kan-series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKanSeries)))
            .andExpect(status().isOk());

        // Validate the KanSeries in the database
        List<KanSeries> kanSeriesList = kanSeriesRepository.findAll();
        assertThat(kanSeriesList).hasSize(databaseSizeBeforeUpdate);
        KanSeries testKanSeries = kanSeriesList.get(kanSeriesList.size() - 1);
        assertThat(testKanSeries.getSeriesId()).isEqualTo(UPDATED_SERIES_ID);
        assertThat(testKanSeries.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testKanSeries.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testKanSeries.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testKanSeries.getWebPage()).isEqualTo(UPDATED_WEB_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingKanSeries() throws Exception {
        int databaseSizeBeforeUpdate = kanSeriesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKanSeriesMockMvc.perform(put("/api/kan-series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kanSeries)))
            .andExpect(status().isBadRequest());

        // Validate the KanSeries in the database
        List<KanSeries> kanSeriesList = kanSeriesRepository.findAll();
        assertThat(kanSeriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKanSeries() throws Exception {
        // Initialize the database
        kanSeriesRepository.saveAndFlush(kanSeries);

        int databaseSizeBeforeDelete = kanSeriesRepository.findAll().size();

        // Delete the kanSeries
        restKanSeriesMockMvc.perform(delete("/api/kan-series/{id}", kanSeries.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KanSeries> kanSeriesList = kanSeriesRepository.findAll();
        assertThat(kanSeriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
