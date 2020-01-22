package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class VinculoFamiliarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculoFamiliar.class);
        VinculoFamiliar vinculoFamiliar1 = new VinculoFamiliar();
        vinculoFamiliar1.setId(1L);
        VinculoFamiliar vinculoFamiliar2 = new VinculoFamiliar();
        vinculoFamiliar2.setId(vinculoFamiliar1.getId());
        assertThat(vinculoFamiliar1).isEqualTo(vinculoFamiliar2);
        vinculoFamiliar2.setId(2L);
        assertThat(vinculoFamiliar1).isNotEqualTo(vinculoFamiliar2);
        vinculoFamiliar1.setId(null);
        assertThat(vinculoFamiliar1).isNotEqualTo(vinculoFamiliar2);
    }
}
