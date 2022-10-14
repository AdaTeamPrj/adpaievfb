package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.HeureSup;
import com.adateam.adpaievfb.repository.HeureSupRepository;
import com.adateam.adpaievfb.service.HeureSupService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HeureSup}.
 */
@Service
@Transactional
public class HeureSupServiceImpl implements HeureSupService {

    private final Logger log = LoggerFactory.getLogger(HeureSupServiceImpl.class);

    private final HeureSupRepository heureSupRepository;

    public HeureSupServiceImpl(HeureSupRepository heureSupRepository) {
        this.heureSupRepository = heureSupRepository;
    }

    @Override
    public HeureSup save(HeureSup heureSup) {
        log.debug("Request to save HeureSup : {}", heureSup);
        return heureSupRepository.save(heureSup);
    }

    @Override
    public HeureSup update(HeureSup heureSup) {
        log.debug("Request to update HeureSup : {}", heureSup);
        return heureSupRepository.save(heureSup);
    }

    @Override
    public Optional<HeureSup> partialUpdate(HeureSup heureSup) {
        log.debug("Request to partially update HeureSup : {}", heureSup);

        return heureSupRepository
            .findById(heureSup.getId())
            .map(existingHeureSup -> {
                if (heureSup.getDate() != null) {
                    existingHeureSup.setDate(heureSup.getDate());
                }
                if (heureSup.getNbHeure() != null) {
                    existingHeureSup.setNbHeure(heureSup.getNbHeure());
                }

                return existingHeureSup;
            })
            .map(heureSupRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeureSup> findAll() {
        log.debug("Request to get all HeureSups");
        return heureSupRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HeureSup> findOne(Long id) {
        log.debug("Request to get HeureSup : {}", id);
        return heureSupRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HeureSup : {}", id);
        heureSupRepository.deleteById(id);
    }
}
