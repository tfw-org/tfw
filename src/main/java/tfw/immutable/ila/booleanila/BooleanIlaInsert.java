package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaInsert {
    private BooleanIlaInsert() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long index, boolean value) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");

        return new MyBooleanIla(ila, index, value);
    }

    private static class MyBooleanIla extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long index;
        private final boolean value;

        MyBooleanIla(BooleanIla ila, long index, boolean value) {
            super(ila.length() + 1);
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(boolean[] array, int offset, long start, int length) throws IOException {
            final long startPlusLength = start + length;

            if (index < start) {
                ila.toArray(array, offset, start - 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.toArray(array, offset, start, indexMinusStart);
                }
                array[offset + indexMinusStart] = value;
                if (index < startPlusLength - 1) {
                    ila.toArray(array, offset + (indexMinusStart + 1), index, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
