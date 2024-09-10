package tfw.immutable.ila.bitila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BItIlaUtilTest {
    @Test
    void testGetPartialLongArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(null, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], -1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], 0, 0, Long.SIZE + 1));
    }

    void testSetPartialLongArguments() {
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(null, 0, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], -1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> BitIlaUtil.setPartialLong(new long[1], 0, 0, Long.SIZE + 1));
    }
}
