package tfw.array;

import junit.framework.TestCase;

/**
 *
 */
public class ArrayUtilTest extends TestCase {
    public void testUnorderedEquals() {
        Object[] array1 = new Object[0];
        Object[] array2 = new Object[0];

        assertTrue("empty arrays", ArrayUtil.unorderedEquals(array1, array2));
        assertFalse("empty & null", ArrayUtil.unorderedEquals(array1, null));
        assertFalse("null & empty", ArrayUtil.unorderedEquals(null, array2));

        array1 = new Object[] {null};
        assertFalse("null element & empty", ArrayUtil.unorderedEquals(array1, array2));
        array2 = new Object[] {new Object()};
        assertFalse("null element & object element", ArrayUtil.unorderedEquals(array1, array2));

        Object o1 = new Object();
        Object o2 = new Object();
        array1 = new Object[] {o1, o2};
        array2 = new Object[] {o2, o1};
        assertTrue("same elements different order", ArrayUtil.unorderedEquals(array1, array2));
        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o2};
        assertFalse("different numbers of different elements", ArrayUtil.unorderedEquals(array1, array2));
        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o1};
        assertTrue("redunant elements in different orders", ArrayUtil.unorderedEquals(array1, array2));
    }
}
