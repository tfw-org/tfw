package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaRemove {
    private ShortIlaRemove() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyShortIla(ila, index);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long index;

        MyShortIla(ShortIla ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws DataInvalidException {
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
