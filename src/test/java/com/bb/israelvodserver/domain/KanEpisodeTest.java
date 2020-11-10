package com.bb.israelvodserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bb.israelvodserver.web.rest.TestUtil;

public class KanEpisodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KanEpisode.class);
        KanEpisode kanEpisode1 = new KanEpisode();
        kanEpisode1.setId(1L);
        KanEpisode kanEpisode2 = new KanEpisode();
        kanEpisode2.setId(kanEpisode1.getId());
        assertThat(kanEpisode1).isEqualTo(kanEpisode2);
        kanEpisode2.setId(2L);
        assertThat(kanEpisode1).isNotEqualTo(kanEpisode2);
        kanEpisode1.setId(null);
        assertThat(kanEpisode1).isNotEqualTo(kanEpisode2);
    }
}
