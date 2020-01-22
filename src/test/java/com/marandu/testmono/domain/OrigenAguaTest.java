package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class OrigenAguaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigenAgua.class);
        OrigenAgua origenAgua1 = new OrigenAgua();
        origenAgua1.setId(1L);
        OrigenAgua origenAgua2 = new OrigenAgua();
        origenAgua2.setId(origenAgua1.getId());
        assertThat(origenAgua1).isEqualTo(origenAgua2);
        origenAgua2.setId(2L);
        assertThat(origenAgua1).isNotEqualTo(origenAgua2);
        origenAgua1.setId(null);
        assertThat(origenAgua1).isNotEqualTo(origenAgua2);
    }
}
