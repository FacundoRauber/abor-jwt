package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class TipoViviendaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoVivienda.class);
        TipoVivienda tipoVivienda1 = new TipoVivienda();
        tipoVivienda1.setId(1L);
        TipoVivienda tipoVivienda2 = new TipoVivienda();
        tipoVivienda2.setId(tipoVivienda1.getId());
        assertThat(tipoVivienda1).isEqualTo(tipoVivienda2);
        tipoVivienda2.setId(2L);
        assertThat(tipoVivienda1).isNotEqualTo(tipoVivienda2);
        tipoVivienda1.setId(null);
        assertThat(tipoVivienda1).isNotEqualTo(tipoVivienda2);
    }
}
