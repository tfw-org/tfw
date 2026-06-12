package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ListECDTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new ListECD(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName == null not allowed!");

        final String testName = "testName";
        final ListECD listECD = new ListECD(testName);

        assertThat(listECD.getEventChannelName()).isEqualTo(testName);
    }
}
