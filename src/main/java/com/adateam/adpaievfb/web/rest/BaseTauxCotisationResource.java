package com.adateam.adpaievfb.web.rest;

import com.adateam.adpaievfb.domain.BaseTauxCotisation;
import com.adateam.adpaievfb.repository.BaseTauxCotisationRepository;
import com.adateam.adpaievfb.service.BaseTauxCotisationService;
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
 * REST controller for managing {@link com.adateam.adpaievfb.domain.BaseTauxCotisation}.
 */
@RestController
@RequestMapping("/api")
public class BaseTauxCotisationResource {

    private final Logger log = LoggerFactory.getLogger(BaseTauxCotisationResource.class);

    private static final String ENTITY_NAME = "baseTauxCotisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaseTauxCotisationService baseTauxCotisationService;

    private final BaseTauxCotisationRepository baseTauxCotisationRepository;

    public BaseTauxCotisationResource(
        BaseTauxCotisationService baseTauxCotisationService,
        BaseTauxCotisationRepository baseTauxCotisationRepository
    ) {
        this.baseTauxCotisationService = baseTauxCotisationService;
        this.baseTauxCotisationRepository = baseTauxCotisationRepository;
    }

    /**
     * {@code POST  /base-taux-cotisations} : Create a new baseTauxCotisation.
     *
     * @param baseTauxCotisation the baseTauxCotisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baseTauxCotisation, or with status {@code 400 (Bad Request)} if the baseTauxCotisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/base-taux-cotisations")
    public ResponseEntity<BaseTauxCotisation> createBaseTauxCotisation(@RequestBody BaseTauxCotisation baseTauxCotisation)
        throws URISyntaxException {
        log.debug("REST request to save BaseTauxCotisation : {}", baseTauxCotisation);
        if (baseTauxCotisation.getId() != null) {
            throw new BadRequestAlertException("A new baseTauxCotisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseTauxCotisation result = baseTauxCotisationService.save(baseTauxCotisation);
        return ResponseEntity
            .created(new URI("/api/base-taux-cotisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /base-taux-cotisations/:id} : Updates an existing baseTauxCotisation.
     *
     * @param id the id of the baseTauxCotisation to save.
     * @param baseTauxCotisation the baseTauxCotisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseTauxCotisation,
     * or with status {@code 400 (Bad Request)} if the baseTauxCotisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baseTauxCotisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/base-taux-cotisations/{id}")
    public ResponseEntity<BaseTauxCotisation> updateBaseTauxCotisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BaseTauxCotisation baseTauxCotisation
    ) throws URISyntaxException {
        log.debug("REST request to update BaseTauxCotisation : {}, {}", id, baseTauxCotisation);
        if (baseTauxCotisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, baseTauxCotisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!baseTauxCotisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BaseTauxCotisation result = baseTauxCotisationService.update(baseTauxCotisation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseTauxCotisation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /base-taux-cotisations/:id} : Partial updates given fields of an existing baseTauxCotisation, field will ignore if it is null
     *
     * @param id the id of the baseTauxCotisation to save.
     * @param baseTauxCotisation the baseTauxCotisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baseTauxCotisation,
     * or with status {@code 400 (Bad Request)} if the baseTauxCotisation is not valid,
     * or with status {@code 404 (Not Found)} if the baseTauxCotisation is not found,
     * or with status {@code 500 (Internal Server Error)} if the baseTauxCotisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/base-taux-cotisations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BaseTauxCotisation> partialUpdateBaseTauxCotisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BaseTauxCotisation baseTauxCotisation
    ) throws URISyntaxException {
        log.debug("REST request to partial update BaseTauxCotisation partially : {}, {}", id, baseTauxCotisation);
        if (baseTauxCotisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, baseTauxCotisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!baseTauxCotisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BaseTauxCotisation> result = baseTauxCotisationService.partialUpdate(baseTauxCotisation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baseTauxCotisation.getId().toString())
        );
    }

    /**
     * {@code GET  /base-taux-cotisations} : get all the baseTauxCotisations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baseTauxCotisations in body.
     */
    @GetMapping("/base-taux-cotisations")
    public List<BaseTauxCotisation> getAllBaseTauxCotisations() {
        log.debug("REST request to get all BaseTauxCotisations");
        return baseTauxCotisationService.findAll();
    }

    /**
     * {@code GET  /base-taux-cotisations/:id} : get the "id" baseTauxCotisation.
     *
     * @param id the id of the baseTauxCotisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baseTauxCotisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/base-taux-cotisations/{id}")
    public ResponseEntity<BaseTauxCotisation> getBaseTauxCotisation(@PathVariable Long id) {
        log.debug("REST request to get BaseTauxCotisation : {}", id);
        Optional<BaseTauxCotisation> baseTauxCotisation = baseTauxCotisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseTauxCotisation);
    }

    /**
     * {@code DELETE  /base-taux-cotisations/:id} : delete the "id" baseTauxCotisation.
     *
     * @param id the id of the baseTauxCotisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/base-taux-cotisations/{id}")
    public ResponseEntity<Void> deleteBaseTauxCotisation(@PathVariable Long id) {
        log.debug("REST request to delete BaseTauxCotisation : {}", id);
        baseTauxCotisationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
