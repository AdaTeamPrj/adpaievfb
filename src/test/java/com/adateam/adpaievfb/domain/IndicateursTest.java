package com.adateam.adpaievfb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adateam.adpaievfb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndicateursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicateurs.class);
        Indicateurs indicateurs1 = new Indicateurs();
        indicateurs1.setId(1L);
        Indicateurs indicateurs2 = new Indicateurs();
        indicateurs2.setId(indicateurs1.getId());
        assertThat(indicateurs1).isEqualTo(indicateurs2);
        indicateurs2.setId(2L);
        assertThat(indicateurs1).isNotEqualTo(indicateurs2);
        indicateurs1.setId(null);
        assertThat(indicateurs1).isNotEqualTo(indicateurs2);
    }
}
