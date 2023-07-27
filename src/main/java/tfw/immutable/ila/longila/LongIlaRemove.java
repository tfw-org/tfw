package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class LongIlaRemove {
    private LongIlaRemove() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyLongIla(ila, index);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long index;

        MyLongIla(LongIla ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(long[] array, int offset, long start, int length) throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index <= start) {
                ila.toArray(array, offset, start + 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                ila.toArray(array, offset, start, indexMinusStart);
                ila.toArray(array, offset + indexMinusStart, index + 1, length - indexMinusStart);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
