package com.trustcoreict.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.trustcoreict.demo.web.rest.TestUtil;

public class HeaderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Header.class);
        Header header1 = new Header();
        header1.setId(1L);
        Header header2 = new Header();
        header2.setId(header1.getId());
        assertThat(header1).isEqualTo(header2);
        header2.setId(2L);
        assertThat(header1).isNotEqualTo(header2);
        header1.setId(null);
        assertThat(header1).isNotEqualTo(header2);
    }
}
