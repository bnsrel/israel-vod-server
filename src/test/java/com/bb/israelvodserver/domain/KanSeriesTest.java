package com.bb.israelvodserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bb.israelvodserver.web.rest.TestUtil;

public class KanSeriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanSeries.class);
        KanSeries kanSeries1 = new KanSeries();
        kanSeries1.setId(1L);
        KanSeries kanSeries2 = new KanSeries();
        kanSeries2.setId(kanSeries1.getId());
        assertThat(kanSeries1).isEqualTo(kanSeries2);
        kanSeries2.setId(2L);
        assertThat(kanSeries1).isNotEqualTo(kanSeries2);
        kanSeries1.setId(null);
        assertThat(kanSeries1).isNotEqualTo(kanSeries2);
    }
}
