package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class TipoServicioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoServicio.class);
        TipoServicio tipoServicio1 = new TipoServicio();
        tipoServicio1.setId(1L);
        TipoServicio tipoServicio2 = new TipoServicio();
        tipoServicio2.setId(tipoServicio1.getId());
        assertThat(tipoServicio1).isEqualTo(tipoServicio2);
        tipoServicio2.setId(2L);
        assertThat(tipoServicio1).isNotEqualTo(tipoServicio2);
        tipoServicio1.setId(null);
        assertThat(tipoServicio1).isNotEqualTo(tipoServicio2);
    }
}
