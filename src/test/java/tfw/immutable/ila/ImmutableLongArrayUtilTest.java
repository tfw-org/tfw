package tfw.immutable.ila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImmutableLongArrayUtilTest {
    @Test
    void boundsCheckTest() {
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(-1, 0, 0, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(0, -1, 0, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(0, 0, -1, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(0, 0, 0, -1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(0, 0, 0, 0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(0, 5, 5, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(5, 0, 0, 5, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(1, 9, 5, 0, 5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(9, 1, 0, 5, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
