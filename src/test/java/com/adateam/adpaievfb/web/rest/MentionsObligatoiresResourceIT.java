package com.adateam.adpaievfb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adateam.adpaievfb.IntegrationTest;
import com.adateam.adpaievfb.domain.MentionsObligatoires;
import com.adateam.adpaievfb.repository.MentionsObligatoiresRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MentionsObligatoiresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MentionsObligatoiresResourceIT {

    private static final String DEFAULT_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_MENTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mentions-obligatoires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MentionsObligatoiresRepository mentionsObligatoiresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMentionsObligatoiresMockMvc;

    private MentionsObligatoires mentionsObligatoires;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentionsObligatoires createEntity(EntityManager em) {
        MentionsObligatoires mentionsObligatoires = new MentionsObligatoires().mention(DEFAULT_MENTION).description(DEFAULT_DESCRIPTION);
        return mentionsObligatoires;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentionsObligatoires createUpdatedEntity(EntityManager em) {
        MentionsObligatoires mentionsObligatoires = new MentionsObligatoires().mention(UPDATED_MENTION).description(UPDATED_DESCRIPTION);
        return mentionsObligatoires;
    }

    @BeforeEach
    public void initTest() {
        mentionsObligatoires = createEntity(em);
    }

    @Test
    @Transactional
    void createMentionsObligatoires() throws Exception {
        int databaseSizeBeforeCreate = mentionsObligatoiresRepository.findAll().size();
        // Create the MentionsObligatoires
        restMentionsObligatoiresMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isCreated());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeCreate + 1);
        MentionsObligatoires testMentionsObligatoires = mentionsObligatoiresList.get(mentionsObligatoiresList.size() - 1);
        assertThat(testMentionsObligatoires.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testMentionsObligatoires.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createMentionsObligatoiresWithExistingId() throws Exception {
        // Create the MentionsObligatoires with an existing ID
        mentionsObligatoires.setId(1L);

        int databaseSizeBeforeCreate = mentionsObligatoiresRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMentionsObligatoiresMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mentionsObligatoiresRepository.findAll().size();
        // set the field null
        mentionsObligatoires.setMention(null);

        // Create the MentionsObligatoires, which fails.

        restMentionsObligatoiresMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mentionsObligatoiresRepository.findAll().size();
        // set the field null
        mentionsObligatoires.setDescription(null);

        // Create the MentionsObligatoires, which fails.

        restMentionsObligatoiresMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMentionsObligatoires() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        // Get all the mentionsObligatoiresList
        restMentionsObligatoiresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentionsObligatoires.getId().intValue())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getMentionsObligatoires() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        // Get the mentionsObligatoires
        restMentionsObligatoiresMockMvc
            .perform(get(ENTITY_API_URL_ID, mentionsObligatoires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mentionsObligatoires.getId().intValue()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingMentionsObligatoires() throws Exception {
        // Get the mentionsObligatoires
        restMentionsObligatoiresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMentionsObligatoires() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();

        // Update the mentionsObligatoires
        MentionsObligatoires updatedMentionsObligatoires = mentionsObligatoiresRepository.findById(mentionsObligatoires.getId()).get();
        // Disconnect from session so that the updates on updatedMentionsObligatoires are not directly saved in db
        em.detach(updatedMentionsObligatoires);
        updatedMentionsObligatoires.mention(UPDATED_MENTION).description(UPDATED_DESCRIPTION);

        restMentionsObligatoiresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMentionsObligatoires.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMentionsObligatoires))
            )
            .andExpect(status().isOk());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
        MentionsObligatoires testMentionsObligatoires = mentionsObligatoiresList.get(mentionsObligatoiresList.size() - 1);
        assertThat(testMentionsObligatoires.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testMentionsObligatoires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mentionsObligatoires.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMentionsObligatoiresWithPatch() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();

        // Update the mentionsObligatoires using partial update
        MentionsObligatoires partialUpdatedMentionsObligatoires = new MentionsObligatoires();
        partialUpdatedMentionsObligatoires.setId(mentionsObligatoires.getId());

        partialUpdatedMentionsObligatoires.mention(UPDATED_MENTION).description(UPDATED_DESCRIPTION);

        restMentionsObligatoiresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentionsObligatoires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentionsObligatoires))
            )
            .andExpect(status().isOk());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
        MentionsObligatoires testMentionsObligatoires = mentionsObligatoiresList.get(mentionsObligatoiresList.size() - 1);
        assertThat(testMentionsObligatoires.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testMentionsObligatoires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateMentionsObligatoiresWithPatch() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();

        // Update the mentionsObligatoires using partial update
        MentionsObligatoires partialUpdatedMentionsObligatoires = new MentionsObligatoires();
        partialUpdatedMentionsObligatoires.setId(mentionsObligatoires.getId());

        partialUpdatedMentionsObligatoires.mention(UPDATED_MENTION).description(UPDATED_DESCRIPTION);

        restMentionsObligatoiresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMentionsObligatoires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMentionsObligatoires))
            )
            .andExpect(status().isOk());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
        MentionsObligatoires testMentionsObligatoires = mentionsObligatoiresList.get(mentionsObligatoiresList.size() - 1);
        assertThat(testMentionsObligatoires.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testMentionsObligatoires.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mentionsObligatoires.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isBadRequest());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMentionsObligatoires() throws Exception {
        int databaseSizeBeforeUpdate = mentionsObligatoiresRepository.findAll().size();
        mentionsObligatoires.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMentionsObligatoiresMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mentionsObligatoires))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MentionsObligatoires in the database
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMentionsObligatoires() throws Exception {
        // Initialize the database
        mentionsObligatoiresRepository.saveAndFlush(mentionsObligatoires);

        int databaseSizeBeforeDelete = mentionsObligatoiresRepository.findAll().size();

        // Delete the mentionsObligatoires
        restMentionsObligatoiresMockMvc
            .perform(delete(ENTITY_API_URL_ID, mentionsObligatoires.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MentionsObligatoires> mentionsObligatoiresList = mentionsObligatoiresRepository.findAll();
        assertThat(mentionsObligatoiresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
