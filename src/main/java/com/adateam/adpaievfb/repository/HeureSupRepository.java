package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.HeureSup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HeureSup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeureSupRepository extends JpaRepository<HeureSup, Long> {}
