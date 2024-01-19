package tfw.immutable.ila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ImmutableLongArrayUtilTest {
    @Test
    void testBoundsCheckIlaLengthLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(-1, 0, 0, 0, 0));
    }

    @Test
    void testBoundsCheckArrayLengthLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(0, -1, 0, 0, 0));
    }

    @Test
    void testBoundsCheckOffsetLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(0, 0, -1, 0, 0));
    }

    @Test
    void testBoundsCheckStartLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(0, 0, 0, -1, 0));
    }

    @Test
    void testBoundsCheckLengthLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(0, 0, 0, 0, -1));
    }

    @Test
    void testBoundsCheckOffsetGreaterThanOrEqualsArrayLength() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(0, 5, 5, 0, 0));
    }

    @Test
    void testBoundsCheckStartGreaterThanOrEqualsIlaLength() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(5, 0, 0, 5, 0));
    }

    @Test
    void testBoundsCheckOffsetPlusLengthGreaterThanArrayLength() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(1, 9, 5, 0, 5));
    }

    @Test
    void testBoundsCheckStartPlusLengthGreaterThanIlaLength() {
        assertThrows(IllegalArgumentException.class, () -> ImmutableLongArrayUtil.boundsCheck(9, 1, 0, 5, 5));
    }
}
