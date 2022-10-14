package com.adateam.adpaievfb.repository;

import com.adateam.adpaievfb.domain.TypeContrat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TypeContratRepositoryWithBagRelationshipsImpl implements TypeContratRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TypeContrat> fetchBagRelationships(Optional<TypeContrat> typeContrat) {
        return typeContrat.map(this::fetchCotisations);
    }

    @Override
    public Page<TypeContrat> fetchBagRelationships(Page<TypeContrat> typeContrats) {
        return new PageImpl<>(
            fetchBagRelationships(typeContrats.getContent()),
            typeContrats.getPageable(),
            typeContrats.getTotalElements()
        );
    }

    @Override
    public List<TypeContrat> fetchBagRelationships(List<TypeContrat> typeContrats) {
        return Optional.of(typeContrats).map(this::fetchCotisations).orElse(Collections.emptyList());
    }

    TypeContrat fetchCotisations(TypeContrat result) {
        return entityManager
            .createQuery(
                "select typeContrat from TypeContrat typeContrat left join fetch typeContrat.cotisations where typeContrat is :typeContrat",
                TypeContrat.class
            )
            .setParameter("typeContrat", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<TypeContrat> fetchCotisations(List<TypeContrat> typeContrats) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, typeContrats.size()).forEach(index -> order.put(typeContrats.get(index).getId(), index));
        List<TypeContrat> result = entityManager
            .createQuery(
                "select distinct typeContrat from TypeContrat typeContrat left join fetch typeContrat.cotisations where typeContrat in :typeContrats",
                TypeContrat.class
            )
            .setParameter("typeContrats", typeContrats)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
