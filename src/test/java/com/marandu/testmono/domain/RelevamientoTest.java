package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class RelevamientoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relevamiento.class);
        Relevamiento relevamiento1 = new Relevamiento();
        relevamiento1.setId(1L);
        Relevamiento relevamiento2 = new Relevamiento();
        relevamiento2.setId(relevamiento1.getId());
        assertThat(relevamiento1).isEqualTo(relevamiento2);
        relevamiento2.setId(2L);
        assertThat(relevamiento1).isNotEqualTo(relevamiento2);
        relevamiento1.setId(null);
        assertThat(relevamiento1).isNotEqualTo(relevamiento2);
    }
}
