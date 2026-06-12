package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class MapECDTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new MapECD(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName == null not allowed!");

        final String testName = "testName";
        final MapECD mapECD = new MapECD(testName);

        assertThat(mapECD.getEventChannelName()).isEqualTo(testName);
    }
}
