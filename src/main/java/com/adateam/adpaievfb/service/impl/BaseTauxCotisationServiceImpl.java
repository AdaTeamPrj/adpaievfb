package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.BaseTauxCotisation;
import com.adateam.adpaievfb.repository.BaseTauxCotisationRepository;
import com.adateam.adpaievfb.service.BaseTauxCotisationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BaseTauxCotisation}.
 */
@Service
@Transactional
public class BaseTauxCotisationServiceImpl implements BaseTauxCotisationService {

    private final Logger log = LoggerFactory.getLogger(BaseTauxCotisationServiceImpl.class);

    private final BaseTauxCotisationRepository baseTauxCotisationRepository;

    public BaseTauxCotisationServiceImpl(BaseTauxCotisationRepository baseTauxCotisationRepository) {
        this.baseTauxCotisationRepository = baseTauxCotisationRepository;
    }

    @Override
    public BaseTauxCotisation save(BaseTauxCotisation baseTauxCotisation) {
        log.debug("Request to save BaseTauxCotisation : {}", baseTauxCotisation);
        return baseTauxCotisationRepository.save(baseTauxCotisation);
    }

    @Override
    public BaseTauxCotisation update(BaseTauxCotisation baseTauxCotisation) {
        log.debug("Request to update BaseTauxCotisation : {}", baseTauxCotisation);
        return baseTauxCotisationRepository.save(baseTauxCotisation);
    }

    @Override
    public Optional<BaseTauxCotisation> partialUpdate(BaseTauxCotisation baseTauxCotisation) {
        log.debug("Request to partially update BaseTauxCotisation : {}", baseTauxCotisation);

        return baseTauxCotisationRepository
            .findById(baseTauxCotisation.getId())
            .map(existingBaseTauxCotisation -> {
                if (baseTauxCotisation.getNom() != null) {
                    existingBaseTauxCotisation.setNom(baseTauxCotisation.getNom());
                }
                if (baseTauxCotisation.getMontant() != null) {
                    existingBaseTauxCotisation.setMontant(baseTauxCotisation.getMontant());
                }

                return existingBaseTauxCotisation;
            })
            .map(baseTauxCotisationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaseTauxCotisation> findAll() {
        log.debug("Request to get all BaseTauxCotisations");
        return baseTauxCotisationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BaseTauxCotisation> findOne(Long id) {
        log.debug("Request to get BaseTauxCotisation : {}", id);
        return baseTauxCotisationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BaseTauxCotisation : {}", id);
        baseTauxCotisationRepository.deleteById(id);
    }
}
