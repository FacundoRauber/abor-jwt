package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class NivelEducativoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelEducativo.class);
        NivelEducativo nivelEducativo1 = new NivelEducativo();
        nivelEducativo1.setId(1L);
        NivelEducativo nivelEducativo2 = new NivelEducativo();
        nivelEducativo2.setId(nivelEducativo1.getId());
        assertThat(nivelEducativo1).isEqualTo(nivelEducativo2);
        nivelEducativo2.setId(2L);
        assertThat(nivelEducativo1).isNotEqualTo(nivelEducativo2);
        nivelEducativo1.setId(null);
        assertThat(nivelEducativo1).isNotEqualTo(nivelEducativo2);
    }
}
