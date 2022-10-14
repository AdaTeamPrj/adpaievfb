package com.adateam.adpaievfb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.adateam.adpaievfb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MentionsObligatoiresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MentionsObligatoires.class);
        MentionsObligatoires mentionsObligatoires1 = new MentionsObligatoires();
        mentionsObligatoires1.setId(1L);
        MentionsObligatoires mentionsObligatoires2 = new MentionsObligatoires();
        mentionsObligatoires2.setId(mentionsObligatoires1.getId());
        assertThat(mentionsObligatoires1).isEqualTo(mentionsObligatoires2);
        mentionsObligatoires2.setId(2L);
        assertThat(mentionsObligatoires1).isNotEqualTo(mentionsObligatoires2);
        mentionsObligatoires1.setId(null);
        assertThat(mentionsObligatoires1).isNotEqualTo(mentionsObligatoires2);
    }
}
