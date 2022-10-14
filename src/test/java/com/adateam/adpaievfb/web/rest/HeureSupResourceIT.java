package com.adateam.adpaievfb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adateam.adpaievfb.IntegrationTest;
import com.adateam.adpaievfb.domain.HeureSup;
import com.adateam.adpaievfb.repository.HeureSupRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link HeureSupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HeureSupResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_NB_HEURE = 1F;
    private static final Float UPDATED_NB_HEURE = 2F;

    private static final String ENTITY_API_URL = "/api/heure-sups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HeureSupRepository heureSupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHeureSupMockMvc;

    private HeureSup heureSup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeureSup createEntity(EntityManager em) {
        HeureSup heureSup = new HeureSup().date(DEFAULT_DATE).nbHeure(DEFAULT_NB_HEURE);
        return heureSup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeureSup createUpdatedEntity(EntityManager em) {
        HeureSup heureSup = new HeureSup().date(UPDATED_DATE).nbHeure(UPDATED_NB_HEURE);
        return heureSup;
    }

    @BeforeEach
    public void initTest() {
        heureSup = createEntity(em);
    }

    @Test
    @Transactional
    void createHeureSup() throws Exception {
        int databaseSizeBeforeCreate = heureSupRepository.findAll().size();
        // Create the HeureSup
        restHeureSupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heureSup)))
            .andExpect(status().isCreated());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeCreate + 1);
        HeureSup testHeureSup = heureSupList.get(heureSupList.size() - 1);
        assertThat(testHeureSup.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHeureSup.getNbHeure()).isEqualTo(DEFAULT_NB_HEURE);
    }

    @Test
    @Transactional
    void createHeureSupWithExistingId() throws Exception {
        // Create the HeureSup with an existing ID
        heureSup.setId(1L);

        int databaseSizeBeforeCreate = heureSupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeureSupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heureSup)))
            .andExpect(status().isBadRequest());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHeureSups() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        // Get all the heureSupList
        restHeureSupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(heureSup.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nbHeure").value(hasItem(DEFAULT_NB_HEURE.doubleValue())));
    }

    @Test
    @Transactional
    void getHeureSup() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        // Get the heureSup
        restHeureSupMockMvc
            .perform(get(ENTITY_API_URL_ID, heureSup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(heureSup.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.nbHeure").value(DEFAULT_NB_HEURE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHeureSup() throws Exception {
        // Get the heureSup
        restHeureSupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHeureSup() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();

        // Update the heureSup
        HeureSup updatedHeureSup = heureSupRepository.findById(heureSup.getId()).get();
        // Disconnect from session so that the updates on updatedHeureSup are not directly saved in db
        em.detach(updatedHeureSup);
        updatedHeureSup.date(UPDATED_DATE).nbHeure(UPDATED_NB_HEURE);

        restHeureSupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHeureSup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHeureSup))
            )
            .andExpect(status().isOk());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
        HeureSup testHeureSup = heureSupList.get(heureSupList.size() - 1);
        assertThat(testHeureSup.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHeureSup.getNbHeure()).isEqualTo(UPDATED_NB_HEURE);
    }

    @Test
    @Transactional
    void putNonExistingHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, heureSup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heureSup))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(heureSup))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(heureSup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHeureSupWithPatch() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();

        // Update the heureSup using partial update
        HeureSup partialUpdatedHeureSup = new HeureSup();
        partialUpdatedHeureSup.setId(heureSup.getId());

        restHeureSupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeureSup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeureSup))
            )
            .andExpect(status().isOk());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
        HeureSup testHeureSup = heureSupList.get(heureSupList.size() - 1);
        assertThat(testHeureSup.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testHeureSup.getNbHeure()).isEqualTo(DEFAULT_NB_HEURE);
    }

    @Test
    @Transactional
    void fullUpdateHeureSupWithPatch() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();

        // Update the heureSup using partial update
        HeureSup partialUpdatedHeureSup = new HeureSup();
        partialUpdatedHeureSup.setId(heureSup.getId());

        partialUpdatedHeureSup.date(UPDATED_DATE).nbHeure(UPDATED_NB_HEURE);

        restHeureSupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHeureSup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHeureSup))
            )
            .andExpect(status().isOk());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
        HeureSup testHeureSup = heureSupList.get(heureSupList.size() - 1);
        assertThat(testHeureSup.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testHeureSup.getNbHeure()).isEqualTo(UPDATED_NB_HEURE);
    }

    @Test
    @Transactional
    void patchNonExistingHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, heureSup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heureSup))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(heureSup))
            )
            .andExpect(status().isBadRequest());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHeureSup() throws Exception {
        int databaseSizeBeforeUpdate = heureSupRepository.findAll().size();
        heureSup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHeureSupMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(heureSup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HeureSup in the database
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHeureSup() throws Exception {
        // Initialize the database
        heureSupRepository.saveAndFlush(heureSup);

        int databaseSizeBeforeDelete = heureSupRepository.findAll().size();

        // Delete the heureSup
        restHeureSupMockMvc
            .perform(delete(ENTITY_API_URL_ID, heureSup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeureSup> heureSupList = heureSupRepository.findAll();
        assertThat(heureSupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
