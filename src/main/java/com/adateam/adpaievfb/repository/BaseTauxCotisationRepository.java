package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.BaseTauxCotisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BaseTauxCotisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseTauxCotisationRepository extends JpaRepository<BaseTauxCotisation, Long> {}
