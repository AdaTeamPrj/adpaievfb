package com.adateam.adpaievfb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adateam.adpaievfb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HeureSupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeureSup.class);
        HeureSup heureSup1 = new HeureSup();
        heureSup1.setId(1L);
        HeureSup heureSup2 = new HeureSup();
        heureSup2.setId(heureSup1.getId());
        assertThat(heureSup1).isEqualTo(heureSup2);
        heureSup2.setId(2L);
        assertThat(heureSup1).isNotEqualTo(heureSup2);
        heureSup1.setId(null);
        assertThat(heureSup1).isNotEqualTo(heureSup2);
    }
}
