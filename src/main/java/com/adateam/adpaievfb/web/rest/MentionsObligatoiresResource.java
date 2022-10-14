package com.adateam.adpaievfb.web.rest;

import com.adateam.adpaievfb.domain.MentionsObligatoires;
import com.adateam.adpaievfb.repository.MentionsObligatoiresRepository;
import com.adateam.adpaievfb.service.MentionsObligatoiresService;
import com.adateam.adpaievfb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.adateam.adpaievfb.domain.MentionsObligatoires}.
 */
@RestController
@RequestMapping("/api")
public class MentionsObligatoiresResource {

    private final Logger log = LoggerFactory.getLogger(MentionsObligatoiresResource.class);

    private static final String ENTITY_NAME = "mentionsObligatoires";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MentionsObligatoiresService mentionsObligatoiresService;

    private final MentionsObligatoiresRepository mentionsObligatoiresRepository;

    public MentionsObligatoiresResource(
        MentionsObligatoiresService mentionsObligatoiresService,
        MentionsObligatoiresRepository mentionsObligatoiresRepository
    ) {
        this.mentionsObligatoiresService = mentionsObligatoiresService;
        this.mentionsObligatoiresRepository = mentionsObligatoiresRepository;
    }

    /**
     * {@code POST  /mentions-obligatoires} : Create a new mentionsObligatoires.
     *
     * @param mentionsObligatoires the mentionsObligatoires to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentionsObligatoires, or with status {@code 400 (Bad Request)} if the mentionsObligatoires has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentions-obligatoires")
    public ResponseEntity<MentionsObligatoires> createMentionsObligatoires(@Valid @RequestBody MentionsObligatoires mentionsObligatoires)
        throws URISyntaxException {
        log.debug("REST request to save MentionsObligatoires : {}", mentionsObligatoires);
        if (mentionsObligatoires.getId() != null) {
            throw new BadRequestAlertException("A new mentionsObligatoires cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MentionsObligatoires result = mentionsObligatoiresService.save(mentionsObligatoires);
        return ResponseEntity
            .created(new URI("/api/mentions-obligatoires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mentions-obligatoires/:id} : Updates an existing mentionsObligatoires.
     *
     * @param id the id of the mentionsObligatoires to save.
     * @param mentionsObligatoires the mentionsObligatoires to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentionsObligatoires,
     * or with status {@code 400 (Bad Request)} if the mentionsObligatoires is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentionsObligatoires couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentions-obligatoires/{id}")
    public ResponseEntity<MentionsObligatoires> updateMentionsObligatoires(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MentionsObligatoires mentionsObligatoires
    ) throws URISyntaxException {
        log.debug("REST request to update MentionsObligatoires : {}, {}", id, mentionsObligatoires);
        if (mentionsObligatoires.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentionsObligatoires.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentionsObligatoiresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MentionsObligatoires result = mentionsObligatoiresService.update(mentionsObligatoires);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentionsObligatoires.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mentions-obligatoires/:id} : Partial updates given fields of an existing mentionsObligatoires, field will ignore if it is null
     *
     * @param id the id of the mentionsObligatoires to save.
     * @param mentionsObligatoires the mentionsObligatoires to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentionsObligatoires,
     * or with status {@code 400 (Bad Request)} if the mentionsObligatoires is not valid,
     * or with status {@code 404 (Not Found)} if the mentionsObligatoires is not found,
     * or with status {@code 500 (Internal Server Error)} if the mentionsObligatoires couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mentions-obligatoires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MentionsObligatoires> partialUpdateMentionsObligatoires(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MentionsObligatoires mentionsObligatoires
    ) throws URISyntaxException {
        log.debug("REST request to partial update MentionsObligatoires partially : {}, {}", id, mentionsObligatoires);
        if (mentionsObligatoires.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mentionsObligatoires.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mentionsObligatoiresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MentionsObligatoires> result = mentionsObligatoiresService.partialUpdate(mentionsObligatoires);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentionsObligatoires.getId().toString())
        );
    }

    /**
     * {@code GET  /mentions-obligatoires} : get all the mentionsObligatoires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mentionsObligatoires in body.
     */
    @GetMapping("/mentions-obligatoires")
    public List<MentionsObligatoires> getAllMentionsObligatoires() {
        log.debug("REST request to get all MentionsObligatoires");
        return mentionsObligatoiresService.findAll();
    }

    /**
     * {@code GET  /mentions-obligatoires/:id} : get the "id" mentionsObligatoires.
     *
     * @param id the id of the mentionsObligatoires to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mentionsObligatoires, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mentions-obligatoires/{id}")
    public ResponseEntity<MentionsObligatoires> getMentionsObligatoires(@PathVariable Long id) {
        log.debug("REST request to get MentionsObligatoires : {}", id);
        Optional<MentionsObligatoires> mentionsObligatoires = mentionsObligatoiresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mentionsObligatoires);
    }

    /**
     * {@code DELETE  /mentions-obligatoires/:id} : delete the "id" mentionsObligatoires.
     *
     * @param id the id of the mentionsObligatoires to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentions-obligatoires/{id}")
    public ResponseEntity<Void> deleteMentionsObligatoires(@PathVariable Long id) {
        log.debug("REST request to delete MentionsObligatoires : {}", id);
        mentionsObligatoiresService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
