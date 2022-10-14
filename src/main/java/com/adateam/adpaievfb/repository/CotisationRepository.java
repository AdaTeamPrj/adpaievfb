package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.Cotisation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cotisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CotisationRepository extends JpaRepository<Cotisation, Long> {}
