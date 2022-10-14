package com.adateam.adpaievfb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adateam.adpaievfb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BaseTauxCotisationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseTauxCotisation.class);
        BaseTauxCotisation baseTauxCotisation1 = new BaseTauxCotisation();
        baseTauxCotisation1.setId(1L);
        BaseTauxCotisation baseTauxCotisation2 = new BaseTauxCotisation();
        baseTauxCotisation2.setId(baseTauxCotisation1.getId());
        assertThat(baseTauxCotisation1).isEqualTo(baseTauxCotisation2);
        baseTauxCotisation2.setId(2L);
        assertThat(baseTauxCotisation1).isNotEqualTo(baseTauxCotisation2);
        baseTauxCotisation1.setId(null);
        assertThat(baseTauxCotisation1).isNotEqualTo(baseTauxCotisation2);
    }
}
