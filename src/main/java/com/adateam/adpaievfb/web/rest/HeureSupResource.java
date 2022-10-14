package com.adateam.adpaievfb.web.rest;

import com.adateam.adpaievfb.domain.HeureSup;
import com.adateam.adpaievfb.repository.HeureSupRepository;
import com.adateam.adpaievfb.service.HeureSupService;
import com.adateam.adpaievfb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.adateam.adpaievfb.domain.HeureSup}.
 */
@RestController
@RequestMapping("/api")
public class HeureSupResource {

    private final Logger log = LoggerFactory.getLogger(HeureSupResource.class);

    private static final String ENTITY_NAME = "heureSup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeureSupService heureSupService;

    private final HeureSupRepository heureSupRepository;

    public HeureSupResource(HeureSupService heureSupService, HeureSupRepository heureSupRepository) {
        this.heureSupService = heureSupService;
        this.heureSupRepository = heureSupRepository;
    }

    /**
     * {@code POST  /heure-sups} : Create a new heureSup.
     *
     * @param heureSup the heureSup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new heureSup, or with status {@code 400 (Bad Request)} if the heureSup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heure-sups")
    public ResponseEntity<HeureSup> createHeureSup(@RequestBody HeureSup heureSup) throws URISyntaxException {
        log.debug("REST request to save HeureSup : {}", heureSup);
        if (heureSup.getId() != null) {
            throw new BadRequestAlertException("A new heureSup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HeureSup result = heureSupService.save(heureSup);
        return ResponseEntity
            .created(new URI("/api/heure-sups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heure-sups/:id} : Updates an existing heureSup.
     *
     * @param id the id of the heureSup to save.
     * @param heureSup the heureSup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heureSup,
     * or with status {@code 400 (Bad Request)} if the heureSup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the heureSup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heure-sups/{id}")
    public ResponseEntity<HeureSup> updateHeureSup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HeureSup heureSup
    ) throws URISyntaxException {
        log.debug("REST request to update HeureSup : {}, {}", id, heureSup);
        if (heureSup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heureSup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heureSupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HeureSup result = heureSupService.update(heureSup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heureSup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /heure-sups/:id} : Partial updates given fields of an existing heureSup, field will ignore if it is null
     *
     * @param id the id of the heureSup to save.
     * @param heureSup the heureSup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated heureSup,
     * or with status {@code 400 (Bad Request)} if the heureSup is not valid,
     * or with status {@code 404 (Not Found)} if the heureSup is not found,
     * or with status {@code 500 (Internal Server Error)} if the heureSup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/heure-sups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeureSup> partialUpdateHeureSup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HeureSup heureSup
    ) throws URISyntaxException {
        log.debug("REST request to partial update HeureSup partially : {}, {}", id, heureSup);
        if (heureSup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, heureSup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!heureSupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeureSup> result = heureSupService.partialUpdate(heureSup);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, heureSup.getId().toString())
        );
    }

    /**
     * {@code GET  /heure-sups} : get all the heureSups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heureSups in body.
     */
    @GetMapping("/heure-sups")
    public List<HeureSup> getAllHeureSups() {
        log.debug("REST request to get all HeureSups");
        return heureSupService.findAll();
    }

    /**
     * {@code GET  /heure-sups/:id} : get the "id" heureSup.
     *
     * @param id the id of the heureSup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the heureSup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heure-sups/{id}")
    public ResponseEntity<HeureSup> getHeureSup(@PathVariable Long id) {
        log.debug("REST request to get HeureSup : {}", id);
        Optional<HeureSup> heureSup = heureSupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(heureSup);
    }

    /**
     * {@code DELETE  /heure-sups/:id} : delete the "id" heureSup.
     *
     * @param id the id of the heureSup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heure-sups/{id}")
    public ResponseEntity<Void> deleteHeureSup(@PathVariable Long id) {
        log.debug("REST request to delete HeureSup : {}", id);
        heureSupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
