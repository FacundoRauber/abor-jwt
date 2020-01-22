package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class NacionalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nacionalidad.class);
        Nacionalidad nacionalidad1 = new Nacionalidad();
        nacionalidad1.setId(1L);
        Nacionalidad nacionalidad2 = new Nacionalidad();
        nacionalidad2.setId(nacionalidad1.getId());
        assertThat(nacionalidad1).isEqualTo(nacionalidad2);
        nacionalidad2.setId(2L);
        assertThat(nacionalidad1).isNotEqualTo(nacionalidad2);
        nacionalidad1.setId(null);
        assertThat(nacionalidad1).isNotEqualTo(nacionalidad2);
    }
}
