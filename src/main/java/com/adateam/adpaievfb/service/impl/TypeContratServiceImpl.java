package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.TypeContrat;
import com.adateam.adpaievfb.repository.TypeContratRepository;
import com.adateam.adpaievfb.service.TypeContratService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeContrat}.
 */
@Service
@Transactional
public class TypeContratServiceImpl implements TypeContratService {

    private final Logger log = LoggerFactory.getLogger(TypeContratServiceImpl.class);

    private final TypeContratRepository typeContratRepository;

    public TypeContratServiceImpl(TypeContratRepository typeContratRepository) {
        this.typeContratRepository = typeContratRepository;
    }

    @Override
    public TypeContrat save(TypeContrat typeContrat) {
        log.debug("Request to save TypeContrat : {}", typeContrat);
        return typeContratRepository.save(typeContrat);
    }

    @Override
    public TypeContrat update(TypeContrat typeContrat) {
        log.debug("Request to update TypeContrat : {}", typeContrat);
        return typeContratRepository.save(typeContrat);
    }

    @Override
    public Optional<TypeContrat> partialUpdate(TypeContrat typeContrat) {
        log.debug("Request to partially update TypeContrat : {}", typeContrat);

        return typeContratRepository
            .findById(typeContrat.getId())
            .map(existingTypeContrat -> {
                if (typeContrat.getTypeContrat() != null) {
                    existingTypeContrat.setTypeContrat(typeContrat.getTypeContrat());
                }

                return existingTypeContrat;
            })
            .map(typeContratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeContrat> findAll() {
        log.debug("Request to get all TypeContrats");
        return typeContratRepository.findAll();
    }

    public Page<TypeContrat> findAllWithEagerRelationships(Pageable pageable) {
        return typeContratRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeContrat> findOne(Long id) {
        log.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeContrat : {}", id);
        typeContratRepository.deleteById(id);
    }
}
