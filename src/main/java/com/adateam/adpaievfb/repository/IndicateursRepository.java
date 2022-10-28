package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.Indicateurs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indicateurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicateursRepository extends JpaRepository<Indicateurs, Long> {}
