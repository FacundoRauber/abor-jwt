package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class OrigenEnergiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigenEnergia.class);
        OrigenEnergia origenEnergia1 = new OrigenEnergia();
        origenEnergia1.setId(1L);
        OrigenEnergia origenEnergia2 = new OrigenEnergia();
        origenEnergia2.setId(origenEnergia1.getId());
        assertThat(origenEnergia1).isEqualTo(origenEnergia2);
        origenEnergia2.setId(2L);
        assertThat(origenEnergia1).isNotEqualTo(origenEnergia2);
        origenEnergia1.setId(null);
        assertThat(origenEnergia1).isNotEqualTo(origenEnergia2);
    }
}
