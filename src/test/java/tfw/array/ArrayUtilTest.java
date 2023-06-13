package tfw.array;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 */
class ArrayUtilTest {
    @Test
    void testUnorderedEquals() {
        Object[] array1 = new Object[0];
        Object[] array2 = new Object[0];

        assertTrue(ArrayUtil.unorderedEquals(array1, array2), "empty arrays");
        assertFalse(ArrayUtil.unorderedEquals(array1, null), "empty & null");
        assertFalse(ArrayUtil.unorderedEquals(null, array2), "null & empty");

        array1 = new Object[] {null};
        assertFalse(ArrayUtil.unorderedEquals(array1, array2), "null element & empty");
        array2 = new Object[] {new Object()};
        assertFalse(ArrayUtil.unorderedEquals(array1, array2), "null element & object element");

        Object o1 = new Object();
        Object o2 = new Object();
        array1 = new Object[] {o1, o2};
        array2 = new Object[] {o2, o1};
        assertTrue(ArrayUtil.unorderedEquals(array1, array2), "same elements different order");
        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o2};
        assertFalse(ArrayUtil.unorderedEquals(array1, array2), "different numbers of different elements");
        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o1};
        assertTrue(ArrayUtil.unorderedEquals(array1, array2), "redunant elements in different orders");
    }
}
