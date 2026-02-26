package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class RootTest {
    @Test
    void isRootedTest() {
        final Root root = Root.builder().setName("test").build();

        assertThat(root.isRooted()).isTrue();
    }
}
