package com.adateam.adpaievfb.service;

import com.adateam.adpaievfb.domain.HeureSup;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link HeureSup}.
 */
public interface HeureSupService {
    /**
     * Save a heureSup.
     *
     * @param heureSup the entity to save.
     * @return the persisted entity.
     */
    HeureSup save(HeureSup heureSup);

    /**
     * Updates a heureSup.
     *
     * @param heureSup the entity to update.
     * @return the persisted entity.
     */
    HeureSup update(HeureSup heureSup);

    /**
     * Partially updates a heureSup.
     *
     * @param heureSup the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HeureSup> partialUpdate(HeureSup heureSup);

    /**
     * Get all the heureSups.
     *
     * @return the list of entities.
     */
    List<HeureSup> findAll();

    /**
     * Get the "id" heureSup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HeureSup> findOne(Long id);

    /**
     * Delete the "id" heureSup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
