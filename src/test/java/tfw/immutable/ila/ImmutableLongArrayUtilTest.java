package tfw.immutable.ila;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

final class ImmutableLongArrayUtilTest {
    @Test
    void boundsCheckAcceptsEmptyRangesAtBoundaries() {
        assertThatCode(() -> ImmutableLongArrayUtil.boundsCheck(0, 0, 0, 0, 0)).doesNotThrowAnyException();
        assertThatCode(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 5, 5, 0)).doesNotThrowAnyException();
        assertThatCode(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 5, 0, 0)).doesNotThrowAnyException();
        assertThatCode(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 0, 5, 0)).doesNotThrowAnyException();
    }

    @Test
    void boundsCheckRejectsNegativeArguments() {
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
    }

    @Test
    void boundsCheckRejectsPositionsBeyondBoundaries() {
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 6, 0, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 0, 6, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void boundsCheckRejectsRangesPastBoundaries() {
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(1, 9, 5, 0, 5))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ImmutableLongArrayUtil.boundsCheck(9, 1, 0, 5, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void boundsCheckAcceptsRangesEndingAtBoundaries() {
        assertThatCode(() -> ImmutableLongArrayUtil.boundsCheck(5, 5, 2, 2, 3)).doesNotThrowAnyException();
    }
}
