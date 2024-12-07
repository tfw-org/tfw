package tfw.array;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ArrayUtilTest {
    @Test
    void unorderedEqualsTest() {
        Object[] array1 = new Object[0];
        Object[] array2 = new Object[0];

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("empty arrays")
                .isTrue();
        assertThat(ArrayUtil.unorderedEquals(array1, null))
                .withFailMessage("empty & null")
                .isFalse();
        assertThat(ArrayUtil.unorderedEquals(null, array2))
                .withFailMessage("null & empty")
                .isFalse();

        array1 = new Object[] {null};

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("null element & empty")
                .isFalse();

        array2 = new Object[] {new Object()};

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("null element & object element")
                .isFalse();

        final Object o1 = new Object();
        final Object o2 = new Object();
        array1 = new Object[] {o1, o2};
        array2 = new Object[] {o2, o1};

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("same elements different order")
                .isTrue();

        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o2};

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("different numbers of different elements")
                .isFalse();

        array1 = new Object[] {o1, o2, o1};
        array2 = new Object[] {o2, o1, o1};

        assertThat(ArrayUtil.unorderedEquals(array1, array2))
                .withFailMessage("redunant elements in different orders")
                .isTrue();
    }
}
