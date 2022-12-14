package com.adateam.adpaievfb.web.rest;

import com.adateam.adpaievfb.domain.Bonus;
import com.adateam.adpaievfb.repository.BonusRepository;
import com.adateam.adpaievfb.service.BonusService;
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
 * REST controller for managing {@link com.adateam.adpaievfb.domain.Bonus}.
 */
@RestController
@RequestMapping("/api")
public class BonusResource {

    private final Logger log = LoggerFactory.getLogger(BonusResource.class);

    private static final String ENTITY_NAME = "bonus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonusService bonusService;

    private final BonusRepository bonusRepository;

    public BonusResource(BonusService bonusService, BonusRepository bonusRepository) {
        this.bonusService = bonusService;
        this.bonusRepository = bonusRepository;
    }

    /**
     * {@code POST  /bonuses} : Create a new bonus.
     *
     * @param bonus the bonus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonus, or with status {@code 400 (Bad Request)} if the bonus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonuses")
    public ResponseEntity<Bonus> createBonus(@Valid @RequestBody Bonus bonus) throws URISyntaxException {
        log.debug("REST request to save Bonus : {}", bonus);
        if (bonus.getId() != null) {
            throw new BadRequestAlertException("A new bonus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bonus result = bonusService.save(bonus);
        return ResponseEntity
            .created(new URI("/api/bonuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonuses/:id} : Updates an existing bonus.
     *
     * @param id the id of the bonus to save.
     * @param bonus the bonus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonus,
     * or with status {@code 400 (Bad Request)} if the bonus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonuses/{id}")
    public ResponseEntity<Bonus> updateBonus(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Bonus bonus)
        throws URISyntaxException {
        log.debug("REST request to update Bonus : {}, {}", id, bonus);
        if (bonus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bonus result = bonusService.update(bonus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bonuses/:id} : Partial updates given fields of an existing bonus, field will ignore if it is null
     *
     * @param id the id of the bonus to save.
     * @param bonus the bonus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonus,
     * or with status {@code 400 (Bad Request)} if the bonus is not valid,
     * or with status {@code 404 (Not Found)} if the bonus is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bonuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bonus> partialUpdateBonus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bonus bonus
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bonus partially : {}, {}", id, bonus);
        if (bonus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bonusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bonus> result = bonusService.partialUpdate(bonus);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonus.getId().toString())
        );
    }

    /**
     * {@code GET  /bonuses} : get all the bonuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonuses in body.
     */
    @GetMapping("/bonuses")
    public List<Bonus> getAllBonuses() {
        log.debug("REST request to get all Bonuses");
        return bonusService.findAll();
    }

    /**
     * {@code GET  /bonuses/:id} : get the "id" bonus.
     *
     * @param id the id of the bonus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonuses/{id}")
    public ResponseEntity<Bonus> getBonus(@PathVariable Long id) {
        log.debug("REST request to get Bonus : {}", id);
        Optional<Bonus> bonus = bonusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonus);
    }

    /**
     * {@code DELETE  /bonuses/:id} : delete the "id" bonus.
     *
     * @param id the id of the bonus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonuses/{id}")
    public ResponseEntity<Void> deleteBonus(@PathVariable Long id) {
        log.debug("REST request to delete Bonus : {}", id);
        bonusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
