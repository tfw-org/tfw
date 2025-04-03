package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class RootTest {
    @Test
    void isRootedTest() {
        RootFactory rootFactory = new RootFactory();
        Root root = rootFactory.create("test", new BasicTransactionQueue());

        assertThat(root.isRooted()).isTrue();
    }
}
