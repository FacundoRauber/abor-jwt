package com.marandu.testmono.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.marandu.testmono.web.rest.TestUtil;

public class TipoPlanAsistenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPlanAsistencia.class);
        TipoPlanAsistencia tipoPlanAsistencia1 = new TipoPlanAsistencia();
        tipoPlanAsistencia1.setId(1L);
        TipoPlanAsistencia tipoPlanAsistencia2 = new TipoPlanAsistencia();
        tipoPlanAsistencia2.setId(tipoPlanAsistencia1.getId());
        assertThat(tipoPlanAsistencia1).isEqualTo(tipoPlanAsistencia2);
        tipoPlanAsistencia2.setId(2L);
        assertThat(tipoPlanAsistencia1).isNotEqualTo(tipoPlanAsistencia2);
        tipoPlanAsistencia1.setId(null);
        assertThat(tipoPlanAsistencia1).isNotEqualTo(tipoPlanAsistencia2);
    }
}
