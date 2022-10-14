package com.adateam.adpaievfb.service.impl;

import com.adateam.adpaievfb.domain.MentionsObligatoires;
import com.adateam.adpaievfb.repository.MentionsObligatoiresRepository;
import com.adateam.adpaievfb.service.MentionsObligatoiresService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MentionsObligatoires}.
 */
@Service
@Transactional
public class MentionsObligatoiresServiceImpl implements MentionsObligatoiresService {

    private final Logger log = LoggerFactory.getLogger(MentionsObligatoiresServiceImpl.class);

    private final MentionsObligatoiresRepository mentionsObligatoiresRepository;

    public MentionsObligatoiresServiceImpl(MentionsObligatoiresRepository mentionsObligatoiresRepository) {
        this.mentionsObligatoiresRepository = mentionsObligatoiresRepository;
    }

    @Override
    public MentionsObligatoires save(MentionsObligatoires mentionsObligatoires) {
        log.debug("Request to save MentionsObligatoires : {}", mentionsObligatoires);
        return mentionsObligatoiresRepository.save(mentionsObligatoires);
    }

    @Override
    public MentionsObligatoires update(MentionsObligatoires mentionsObligatoires) {
        log.debug("Request to update MentionsObligatoires : {}", mentionsObligatoires);
        return mentionsObligatoiresRepository.save(mentionsObligatoires);
    }

    @Override
    public Optional<MentionsObligatoires> partialUpdate(MentionsObligatoires mentionsObligatoires) {
        log.debug("Request to partially update MentionsObligatoires : {}", mentionsObligatoires);

        return mentionsObligatoiresRepository
            .findById(mentionsObligatoires.getId())
            .map(existingMentionsObligatoires -> {
                if (mentionsObligatoires.getMention() != null) {
                    existingMentionsObligatoires.setMention(mentionsObligatoires.getMention());
                }
                if (mentionsObligatoires.getDescription() != null) {
                    existingMentionsObligatoires.setDescription(mentionsObligatoires.getDescription());
                }

                return existingMentionsObligatoires;
            })
            .map(mentionsObligatoiresRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentionsObligatoires> findAll() {
        log.debug("Request to get all MentionsObligatoires");
        return mentionsObligatoiresRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MentionsObligatoires> findOne(Long id) {
        log.debug("Request to get MentionsObligatoires : {}", id);
        return mentionsObligatoiresRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MentionsObligatoires : {}", id);
        mentionsObligatoiresRepository.deleteById(id);
    }
}
