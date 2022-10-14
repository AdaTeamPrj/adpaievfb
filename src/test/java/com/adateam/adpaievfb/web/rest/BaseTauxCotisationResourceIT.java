package com.adateam.adpaievfb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adateam.adpaievfb.IntegrationTest;
import com.adateam.adpaievfb.domain.BaseTauxCotisation;
import com.adateam.adpaievfb.repository.BaseTauxCotisationRepository;
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
 * Integration tests for the {@link BaseTauxCotisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BaseTauxCotisationResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final String ENTITY_API_URL = "/api/base-taux-cotisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BaseTauxCotisationRepository baseTauxCotisationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBaseTauxCotisationMockMvc;

    private BaseTauxCotisation baseTauxCotisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseTauxCotisation createEntity(EntityManager em) {
        BaseTauxCotisation baseTauxCotisation = new BaseTauxCotisation().nom(DEFAULT_NOM).montant(DEFAULT_MONTANT);
        return baseTauxCotisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseTauxCotisation createUpdatedEntity(EntityManager em) {
        BaseTauxCotisation baseTauxCotisation = new BaseTauxCotisation().nom(UPDATED_NOM).montant(UPDATED_MONTANT);
        return baseTauxCotisation;
    }

    @BeforeEach
    public void initTest() {
        baseTauxCotisation = createEntity(em);
    }

    @Test
    @Transactional
    void createBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeCreate = baseTauxCotisationRepository.findAll().size();
        // Create the BaseTauxCotisation
        restBaseTauxCotisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isCreated());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeCreate + 1);
        BaseTauxCotisation testBaseTauxCotisation = baseTauxCotisationList.get(baseTauxCotisationList.size() - 1);
        assertThat(testBaseTauxCotisation.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testBaseTauxCotisation.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void createBaseTauxCotisationWithExistingId() throws Exception {
        // Create the BaseTauxCotisation with an existing ID
        baseTauxCotisation.setId(1L);

        int databaseSizeBeforeCreate = baseTauxCotisationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseTauxCotisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBaseTauxCotisations() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        // Get all the baseTauxCotisationList
        restBaseTauxCotisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseTauxCotisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    void getBaseTauxCotisation() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        // Get the baseTauxCotisation
        restBaseTauxCotisationMockMvc
            .perform(get(ENTITY_API_URL_ID, baseTauxCotisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(baseTauxCotisation.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingBaseTauxCotisation() throws Exception {
        // Get the baseTauxCotisation
        restBaseTauxCotisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBaseTauxCotisation() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();

        // Update the baseTauxCotisation
        BaseTauxCotisation updatedBaseTauxCotisation = baseTauxCotisationRepository.findById(baseTauxCotisation.getId()).get();
        // Disconnect from session so that the updates on updatedBaseTauxCotisation are not directly saved in db
        em.detach(updatedBaseTauxCotisation);
        updatedBaseTauxCotisation.nom(UPDATED_NOM).montant(UPDATED_MONTANT);

        restBaseTauxCotisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBaseTauxCotisation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBaseTauxCotisation))
            )
            .andExpect(status().isOk());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
        BaseTauxCotisation testBaseTauxCotisation = baseTauxCotisationList.get(baseTauxCotisationList.size() - 1);
        assertThat(testBaseTauxCotisation.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBaseTauxCotisation.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void putNonExistingBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, baseTauxCotisation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBaseTauxCotisationWithPatch() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();

        // Update the baseTauxCotisation using partial update
        BaseTauxCotisation partialUpdatedBaseTauxCotisation = new BaseTauxCotisation();
        partialUpdatedBaseTauxCotisation.setId(baseTauxCotisation.getId());

        partialUpdatedBaseTauxCotisation.nom(UPDATED_NOM);

        restBaseTauxCotisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBaseTauxCotisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBaseTauxCotisation))
            )
            .andExpect(status().isOk());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
        BaseTauxCotisation testBaseTauxCotisation = baseTauxCotisationList.get(baseTauxCotisationList.size() - 1);
        assertThat(testBaseTauxCotisation.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBaseTauxCotisation.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void fullUpdateBaseTauxCotisationWithPatch() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();

        // Update the baseTauxCotisation using partial update
        BaseTauxCotisation partialUpdatedBaseTauxCotisation = new BaseTauxCotisation();
        partialUpdatedBaseTauxCotisation.setId(baseTauxCotisation.getId());

        partialUpdatedBaseTauxCotisation.nom(UPDATED_NOM).montant(UPDATED_MONTANT);

        restBaseTauxCotisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBaseTauxCotisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBaseTauxCotisation))
            )
            .andExpect(status().isOk());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
        BaseTauxCotisation testBaseTauxCotisation = baseTauxCotisationList.get(baseTauxCotisationList.size() - 1);
        assertThat(testBaseTauxCotisation.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testBaseTauxCotisation.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void patchNonExistingBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, baseTauxCotisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBaseTauxCotisation() throws Exception {
        int databaseSizeBeforeUpdate = baseTauxCotisationRepository.findAll().size();
        baseTauxCotisation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseTauxCotisationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(baseTauxCotisation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BaseTauxCotisation in the database
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBaseTauxCotisation() throws Exception {
        // Initialize the database
        baseTauxCotisationRepository.saveAndFlush(baseTauxCotisation);

        int databaseSizeBeforeDelete = baseTauxCotisationRepository.findAll().size();

        // Delete the baseTauxCotisation
        restBaseTauxCotisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, baseTauxCotisation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BaseTauxCotisation> baseTauxCotisationList = baseTauxCotisationRepository.findAll();
        assertThat(baseTauxCotisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
