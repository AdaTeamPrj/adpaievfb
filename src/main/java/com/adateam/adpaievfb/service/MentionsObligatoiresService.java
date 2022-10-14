package com.adateam.adpaievfb.service;

import com.adateam.adpaievfb.domain.MentionsObligatoires;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MentionsObligatoires}.
 */
public interface MentionsObligatoiresService {
    /**
     * Save a mentionsObligatoires.
     *
     * @param mentionsObligatoires the entity to save.
     * @return the persisted entity.
     */
    MentionsObligatoires save(MentionsObligatoires mentionsObligatoires);

    /**
     * Updates a mentionsObligatoires.
     *
     * @param mentionsObligatoires the entity to update.
     * @return the persisted entity.
     */
    MentionsObligatoires update(MentionsObligatoires mentionsObligatoires);

    /**
     * Partially updates a mentionsObligatoires.
     *
     * @param mentionsObligatoires the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MentionsObligatoires> partialUpdate(MentionsObligatoires mentionsObligatoires);

    /**
     * Get all the mentionsObligatoires.
     *
     * @return the list of entities.
     */
    List<MentionsObligatoires> findAll();

    /**
     * Get the "id" mentionsObligatoires.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MentionsObligatoires> findOne(Long id);

    /**
     * Delete the "id" mentionsObligatoires.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
