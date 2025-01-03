package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class BItIlaUtilTest {
    @Test
    void getPartialLongArgumentsTest() {
        assertThatThrownBy(() -> BitIlaUtil.getPartialLong(null, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.getPartialLong(new long[1], -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.getPartialLong(new long[1], 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.getPartialLong(new long[1], 32, 32))
                .isInstanceOf(IllegalArgumentException.class);
    }

    void setPartialLongArgumentsTest() {
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(null, 0, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], -1, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], 0, 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], 0, 0, Long.SIZE + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
