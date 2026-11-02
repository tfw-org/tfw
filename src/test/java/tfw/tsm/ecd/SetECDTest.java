package tfw.tsm.ecd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class SetECDTest {
    @Test
    void constructionTest() {
        assertThatThrownBy(() -> new SetECD(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eventChannelName == null not allowed!");

        final String testName = "testName";
        final SetECD setECD = new SetECD(testName);

        assertThat(setECD.getEventChannelName()).isEqualTo(testName);
    }
}
