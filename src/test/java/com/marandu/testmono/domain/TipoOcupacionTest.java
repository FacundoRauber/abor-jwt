package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class TipoOcupacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoOcupacion.class);
        TipoOcupacion tipoOcupacion1 = new TipoOcupacion();
        tipoOcupacion1.setId(1L);
        TipoOcupacion tipoOcupacion2 = new TipoOcupacion();
        tipoOcupacion2.setId(tipoOcupacion1.getId());
        assertThat(tipoOcupacion1).isEqualTo(tipoOcupacion2);
        tipoOcupacion2.setId(2L);
        assertThat(tipoOcupacion1).isNotEqualTo(tipoOcupacion2);
        tipoOcupacion1.setId(null);
        assertThat(tipoOcupacion1).isNotEqualTo(tipoOcupacion2);
    }
}
