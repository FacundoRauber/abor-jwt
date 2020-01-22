package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class ComunidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comunidad.class);
        Comunidad comunidad1 = new Comunidad();
        comunidad1.setId(1L);
        Comunidad comunidad2 = new Comunidad();
        comunidad2.setId(comunidad1.getId());
        assertThat(comunidad1).isEqualTo(comunidad2);
        comunidad2.setId(2L);
        assertThat(comunidad1).isNotEqualTo(comunidad2);
        comunidad1.setId(null);
        assertThat(comunidad1).isNotEqualTo(comunidad2);
    }
}
