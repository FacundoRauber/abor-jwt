package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class TipoTratamientoBasuraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTratamientoBasura.class);
        TipoTratamientoBasura tipoTratamientoBasura1 = new TipoTratamientoBasura();
        tipoTratamientoBasura1.setId(1L);
        TipoTratamientoBasura tipoTratamientoBasura2 = new TipoTratamientoBasura();
        tipoTratamientoBasura2.setId(tipoTratamientoBasura1.getId());
        assertThat(tipoTratamientoBasura1).isEqualTo(tipoTratamientoBasura2);
        tipoTratamientoBasura2.setId(2L);
        assertThat(tipoTratamientoBasura1).isNotEqualTo(tipoTratamientoBasura2);
        tipoTratamientoBasura1.setId(null);
        assertThat(tipoTratamientoBasura1).isNotEqualTo(tipoTratamientoBasura2);
    }
}
