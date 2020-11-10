package com.bb.israelvodserver.web.rest;

import com.bb.israelvodserver.domain.KanEpisode;
import com.bb.israelvodserver.repository.KanEpisodeRepository;
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
 * REST controller for managing {@link com.bb.israelvodserver.domain.KanEpisode}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KanEpisodeResource {

    private final Logger log = LoggerFactory.getLogger(KanEpisodeResource.class);

    private static final String ENTITY_NAME = "kanEpisode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KanEpisodeRepository kanEpisodeRepository;

    public KanEpisodeResource(KanEpisodeRepository kanEpisodeRepository) {
        this.kanEpisodeRepository = kanEpisodeRepository;
    }

    /**
     * {@code POST  /kan-episodes} : Create a new kanEpisode.
     *
     * @param kanEpisode the kanEpisode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kanEpisode, or with status {@code 400 (Bad Request)} if the kanEpisode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kan-episodes")
    public ResponseEntity<KanEpisode> createKanEpisode(@RequestBody KanEpisode kanEpisode) throws URISyntaxException {
        log.debug("REST request to save KanEpisode : {}", kanEpisode);
        if (kanEpisode.getId() != null) {
            throw new BadRequestAlertException("A new kanEpisode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KanEpisode result = kanEpisodeRepository.save(kanEpisode);
        return ResponseEntity.created(new URI("/api/kan-episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kan-episodes} : Updates an existing kanEpisode.
     *
     * @param kanEpisode the kanEpisode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kanEpisode,
     * or with status {@code 400 (Bad Request)} if the kanEpisode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kanEpisode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kan-episodes")
    public ResponseEntity<KanEpisode> updateKanEpisode(@RequestBody KanEpisode kanEpisode) throws URISyntaxException {
        log.debug("REST request to update KanEpisode : {}", kanEpisode);
        if (kanEpisode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KanEpisode result = kanEpisodeRepository.save(kanEpisode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kanEpisode.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kan-episodes} : get all the kanEpisodes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kanEpisodes in body.
     */
    @GetMapping("/kan-episodes")
    public ResponseEntity<List<KanEpisode>> getAllKanEpisodes(Pageable pageable) {
        log.debug("REST request to get a page of KanEpisodes");
        Page<KanEpisode> page = kanEpisodeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kan-episodes/:id} : get the "id" kanEpisode.
     *
     * @param id the id of the kanEpisode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kanEpisode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kan-episodes/{id}")
    public ResponseEntity<KanEpisode> getKanEpisode(@PathVariable Long id) {
        log.debug("REST request to get KanEpisode : {}", id);
        Optional<KanEpisode> kanEpisode = kanEpisodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kanEpisode);
    }

    /**
     * {@code DELETE  /kan-episodes/:id} : delete the "id" kanEpisode.
     *
     * @param id the id of the kanEpisode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kan-episodes/{id}")
    public ResponseEntity<Void> deleteKanEpisode(@PathVariable Long id) {
        log.debug("REST request to delete KanEpisode : {}", id);
        kanEpisodeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
