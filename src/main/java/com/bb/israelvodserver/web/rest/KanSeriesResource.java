package com.bb.israelvodserver.web.rest;

import com.bb.israelvodserver.domain.KanSeries;
import com.bb.israelvodserver.repository.KanSeriesRepository;
import com.bb.israelvodserver.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bb.israelvodserver.domain.KanSeries}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KanSeriesResource {

    private final Logger log = LoggerFactory.getLogger(KanSeriesResource.class);

    private static final String ENTITY_NAME = "kanSeries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KanSeriesRepository kanSeriesRepository;

    public KanSeriesResource(KanSeriesRepository kanSeriesRepository) {
        this.kanSeriesRepository = kanSeriesRepository;
    }

    /**
     * {@code POST  /kan-series} : Create a new kanSeries.
     *
     * @param kanSeries the kanSeries to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kanSeries, or with status {@code 400 (Bad Request)} if the kanSeries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kan-series")
    public ResponseEntity<KanSeries> createKanSeries(@RequestBody KanSeries kanSeries) throws URISyntaxException {
        log.debug("REST request to save KanSeries : {}", kanSeries);
        if (kanSeries.getId() != null) {
            throw new BadRequestAlertException("A new kanSeries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KanSeries result = kanSeriesRepository.save(kanSeries);
        return ResponseEntity.created(new URI("/api/kan-series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kan-series} : Updates an existing kanSeries.
     *
     * @param kanSeries the kanSeries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kanSeries,
     * or with status {@code 400 (Bad Request)} if the kanSeries is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kanSeries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kan-series")
    public ResponseEntity<KanSeries> updateKanSeries(@RequestBody KanSeries kanSeries) throws URISyntaxException {
        log.debug("REST request to update KanSeries : {}", kanSeries);
        if (kanSeries.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KanSeries result = kanSeriesRepository.save(kanSeries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kanSeries.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kan-series} : get all the kanSeries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kanSeries in body.
     */
    @GetMapping("/kan-series")
    public ResponseEntity<List<KanSeries>> getAllKanSeries(Pageable pageable) {
        log.debug("REST request to get a page of KanSeries");
        Page<KanSeries> page = kanSeriesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kan-series/:id} : get the "id" kanSeries.
     *
     * @param id the id of the kanSeries to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kanSeries, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kan-series/{id}")
    public ResponseEntity<KanSeries> getKanSeries(@PathVariable Long id) {
        log.debug("REST request to get KanSeries : {}", id);
        Optional<KanSeries> kanSeries = kanSeriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kanSeries);
    }

    /**
     * {@code DELETE  /kan-series/:id} : delete the "id" kanSeries.
     *
     * @param id the id of the kanSeries to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kan-series/{id}")
    public ResponseEntity<Void> deleteKanSeries(@PathVariable Long id) {
        log.debug("REST request to delete KanSeries : {}", id);
        kanSeriesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
