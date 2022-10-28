package com.adateam.adpaievfb.web.rest;

import com.adateam.adpaievfb.domain.Indicateurs;
import com.adateam.adpaievfb.repository.IndicateursRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.adateam.adpaievfb.domain.Indicateurs}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndicateursResource {

    private final Logger log = LoggerFactory.getLogger(IndicateursResource.class);

    private final IndicateursRepository indicateursRepository;

    public IndicateursResource(IndicateursRepository indicateursRepository) {
        this.indicateursRepository = indicateursRepository;
    }

    /**
     * {@code GET  /indicateurs} : get all the indicateurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indicateurs in body.
     */
    @GetMapping("/indicateurs")
    public List<Indicateurs> getAllIndicateurs() {
        log.debug("REST request to get all Indicateurs");
        return indicateursRepository.findAll();
    }

    /**
     * {@code GET  /indicateurs/:id} : get the "id" indicateurs.
     *
     * @param id the id of the indicateurs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indicateurs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/indicateurs/{id}")
    public ResponseEntity<Indicateurs> getIndicateurs(@PathVariable Long id) {
        log.debug("REST request to get Indicateurs : {}", id);
        Optional<Indicateurs> indicateurs = indicateursRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(indicateurs);
    }
}
