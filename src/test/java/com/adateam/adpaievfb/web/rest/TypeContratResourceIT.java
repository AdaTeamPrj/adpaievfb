package com.adateam.adpaievfb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adateam.adpaievfb.IntegrationTest;
import com.adateam.adpaievfb.domain.TypeContrat;
import com.adateam.adpaievfb.domain.enumeration.TypeDeContrat;
import com.adateam.adpaievfb.repository.TypeContratRepository;
import com.adateam.adpaievfb.service.TypeContratService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TypeContratResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TypeContratResourceIT {

    private static final TypeDeContrat DEFAULT_TYPE_CONTRAT = TypeDeContrat.Stage;
    private static final TypeDeContrat UPDATED_TYPE_CONTRAT = TypeDeContrat.Alternance;

    private static final String ENTITY_API_URL = "/api/type-contrats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeContratRepository typeContratRepository;

    @Mock
    private TypeContratRepository typeContratRepositoryMock;

    @Mock
    private TypeContratService typeContratServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeContratMockMvc;

    private TypeContrat typeContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createEntity(EntityManager em) {
        TypeContrat typeContrat = new TypeContrat().typeContrat(DEFAULT_TYPE_CONTRAT);
        return typeContrat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeContrat createUpdatedEntity(EntityManager em) {
        TypeContrat typeContrat = new TypeContrat().typeContrat(UPDATED_TYPE_CONTRAT);
        return typeContrat;
    }

    @BeforeEach
    public void initTest() {
        typeContrat = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();
        // Create the TypeContrat
        restTypeContratMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isCreated());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate + 1);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getTypeContrat()).isEqualTo(DEFAULT_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    void createTypeContratWithExistingId() throws Exception {
        // Create the TypeContrat with an existing ID
        typeContrat.setId(1L);

        int databaseSizeBeforeCreate = typeContratRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeContratMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeContratRepository.findAll().size();
        // set the field null
        typeContrat.setTypeContrat(null);

        // Create the TypeContrat, which fails.

        restTypeContratMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isBadRequest());

        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeContrats() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get all the typeContratList
        restTypeContratMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeContrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeContrat").value(hasItem(DEFAULT_TYPE_CONTRAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTypeContratsWithEagerRelationshipsIsEnabled() throws Exception {
        when(typeContratServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTypeContratMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(typeContratServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTypeContratsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(typeContratServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTypeContratMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(typeContratRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTypeContrat() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        // Get the typeContrat
        restTypeContratMockMvc
            .perform(get(ENTITY_API_URL_ID, typeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeContrat.getId().intValue()))
            .andExpect(jsonPath("$.typeContrat").value(DEFAULT_TYPE_CONTRAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTypeContrat() throws Exception {
        // Get the typeContrat
        restTypeContratMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeContrat() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Update the typeContrat
        TypeContrat updatedTypeContrat = typeContratRepository.findById(typeContrat.getId()).get();
        // Disconnect from session so that the updates on updatedTypeContrat are not directly saved in db
        em.detach(updatedTypeContrat);
        updatedTypeContrat.typeContrat(UPDATED_TYPE_CONTRAT);

        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getTypeContrat()).isEqualTo(UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    void putNonExistingTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeContrat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeContratWithPatch() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Update the typeContrat using partial update
        TypeContrat partialUpdatedTypeContrat = new TypeContrat();
        partialUpdatedTypeContrat.setId(typeContrat.getId());

        partialUpdatedTypeContrat.typeContrat(UPDATED_TYPE_CONTRAT);

        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getTypeContrat()).isEqualTo(UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    void fullUpdateTypeContratWithPatch() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();

        // Update the typeContrat using partial update
        TypeContrat partialUpdatedTypeContrat = new TypeContrat();
        partialUpdatedTypeContrat.setId(typeContrat.getId());

        partialUpdatedTypeContrat.typeContrat(UPDATED_TYPE_CONTRAT);

        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
        TypeContrat testTypeContrat = typeContratList.get(typeContratList.size() - 1);
        assertThat(testTypeContrat.getTypeContrat()).isEqualTo(UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    void patchNonExistingTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = typeContratRepository.findAll().size();
        typeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeContrat in the database
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeContrat() throws Exception {
        // Initialize the database
        typeContratRepository.saveAndFlush(typeContrat);

        int databaseSizeBeforeDelete = typeContratRepository.findAll().size();

        // Delete the typeContrat
        restTypeContratMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeContrat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeContrat> typeContratList = typeContratRepository.findAll();
        assertThat(typeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
