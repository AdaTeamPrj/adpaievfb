package com.adateam.adpaievfb.service;

import com.adateam.adpaievfb.domain.TypeContrat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TypeContrat}.
 */
public interface TypeContratService {
    /**
     * Save a typeContrat.
     *
     * @param typeContrat the entity to save.
     * @return the persisted entity.
     */
    TypeContrat save(TypeContrat typeContrat);

    /**
     * Updates a typeContrat.
     *
     * @param typeContrat the entity to update.
     * @return the persisted entity.
     */
    TypeContrat update(TypeContrat typeContrat);

    /**
     * Partially updates a typeContrat.
     *
     * @param typeContrat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeContrat> partialUpdate(TypeContrat typeContrat);

    /**
     * Get all the typeContrats.
     *
     * @return the list of entities.
     */
    List<TypeContrat> findAll();

    /**
     * Get all the typeContrats with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeContrat> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" typeContrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeContrat> findOne(Long id);

    /**
     * Delete the "id" typeContrat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
