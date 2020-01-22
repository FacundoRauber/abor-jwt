package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class IntegranteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Integrante.class);
        Integrante integrante1 = new Integrante();
        integrante1.setId(1L);
        Integrante integrante2 = new Integrante();
        integrante2.setId(integrante1.getId());
        assertThat(integrante1).isEqualTo(integrante2);
        integrante2.setId(2L);
        assertThat(integrante1).isNotEqualTo(integrante2);
        integrante1.setId(null);
        assertThat(integrante1).isNotEqualTo(integrante2);
    }
}
