package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.TypeContrat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TypeContratRepositoryWithBagRelationships {
    Optional<TypeContrat> fetchBagRelationships(Optional<TypeContrat> typeContrat);

    List<TypeContrat> fetchBagRelationships(List<TypeContrat> typeContrats);

    Page<TypeContrat> fetchBagRelationships(Page<TypeContrat> typeContrats);
}
