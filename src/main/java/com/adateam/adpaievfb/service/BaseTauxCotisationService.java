package com.adateam.adpaievfb.service;

import com.adateam.adpaievfb.domain.BaseTauxCotisation;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BaseTauxCotisation}.
 */
public interface BaseTauxCotisationService {
    /**
     * Save a baseTauxCotisation.
     *
     * @param baseTauxCotisation the entity to save.
     * @return the persisted entity.
     */
    BaseTauxCotisation save(BaseTauxCotisation baseTauxCotisation);

    /**
     * Updates a baseTauxCotisation.
     *
     * @param baseTauxCotisation the entity to update.
     * @return the persisted entity.
     */
    BaseTauxCotisation update(BaseTauxCotisation baseTauxCotisation);

    /**
     * Partially updates a baseTauxCotisation.
     *
     * @param baseTauxCotisation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BaseTauxCotisation> partialUpdate(BaseTauxCotisation baseTauxCotisation);

    /**
     * Get all the baseTauxCotisations.
     *
     * @return the list of entities.
     */
    List<BaseTauxCotisation> findAll();

    /**
     * Get the "id" baseTauxCotisation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseTauxCotisation> findOne(Long id);

    /**
     * Delete the "id" baseTauxCotisation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
