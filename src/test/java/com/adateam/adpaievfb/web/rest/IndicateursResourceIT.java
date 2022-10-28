package com.adateam.adpaievfb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adateam.adpaievfb.IntegrationTest;
import com.adateam.adpaievfb.domain.Indicateurs;
import com.adateam.adpaievfb.repository.IndicateursRepository;
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
 * Integration tests for the {@link IndicateursResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndicateursResourceIT {

    private static final String DEFAULT_GESTIONDEPAIE = "AAAAAAAAAA";
    private static final String UPDATED_GESTIONDEPAIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/indicateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndicateursRepository indicateursRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndicateursMockMvc;

    private Indicateurs indicateurs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicateurs createEntity(EntityManager em) {
        Indicateurs indicateurs = new Indicateurs().gestiondepaie(DEFAULT_GESTIONDEPAIE);
        return indicateurs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Indicateurs createUpdatedEntity(EntityManager em) {
        Indicateurs indicateurs = new Indicateurs().gestiondepaie(UPDATED_GESTIONDEPAIE);
        return indicateurs;
    }

    @BeforeEach
    public void initTest() {
        indicateurs = createEntity(em);
    }

    @Test
    @Transactional
    void getAllIndicateurs() throws Exception {
        // Initialize the database
        indicateursRepository.saveAndFlush(indicateurs);

        // Get all the indicateursList
        restIndicateursMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicateurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].gestiondepaie").value(hasItem(DEFAULT_GESTIONDEPAIE)));
    }

    @Test
    @Transactional
    void getIndicateurs() throws Exception {
        // Initialize the database
        indicateursRepository.saveAndFlush(indicateurs);

        // Get the indicateurs
        restIndicateursMockMvc
            .perform(get(ENTITY_API_URL_ID, indicateurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indicateurs.getId().intValue()))
            .andExpect(jsonPath("$.gestiondepaie").value(DEFAULT_GESTIONDEPAIE));
    }

    @Test
    @Transactional
    void getNonExistingIndicateurs() throws Exception {
        // Get the indicateurs
        restIndicateursMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
