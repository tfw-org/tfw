package tfw.immutable.ila.bitila;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThatThrownBy(() -> BitIlaUtil.getPartialLong(new long[1], 32, 33))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getPartialLongExactBoundaryTest() {
        final long value = 0x0123456789ABCDEFL;

        assertThat(BitIlaUtil.getPartialLong(new long[] {value}, 0, Long.SIZE)).isEqualTo(value);
    }

    @Test
    void getPartialLongZeroLengthAtEndTest() {
        assertThat(BitIlaUtil.getPartialLong(new long[] {0L}, Long.SIZE, 0)).isZero();
    }

    @Test
    void setPartialLongArgumentsTest() {
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(null, 0, 0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], -1, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], 0, 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> BitIlaUtil.setPartialLong(new long[1], 0, 0, Long.SIZE + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void copyExactArrayLengthTest() {
        final long value = 0x0123456789ABCDEFL;
        final long[] actual = new long[1];

        BitIlaUtil.copy(actual, 0, new long[] {value}, 0, Long.SIZE);

        assertThat(actual).containsExactly(value);
    }
}
