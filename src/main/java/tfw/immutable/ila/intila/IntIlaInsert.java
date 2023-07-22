package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class IntIlaInsert {
    private IntIlaInsert() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long index, int value) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");

        return new MyIntIla(ila, index, value);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final IntIla ila;
        private final long index;
        private final int value;

        MyIntIla(IntIla ila, long index, int value) {
            super(ila.length() + 1);
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index < start) {
                ila.toArray(array, offset, stride, start - 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, stride, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.toArray(array, offset, stride, start, indexMinusStart);
                }
                array[offset + indexMinusStart * stride] = value;
                if (index < startPlusLength - 1) {
                    ila.toArray(
                            array,
                            offset + (indexMinusStart + 1) * stride,
                            stride,
                            index,
                            length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
