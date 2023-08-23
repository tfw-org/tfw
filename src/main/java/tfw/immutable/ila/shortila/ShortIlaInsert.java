package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaInsert {
    private ShortIlaInsert() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long index, short value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");

        return new MyShortIla(ila, index, value);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long index;
        private final short value;

        MyShortIla(ShortIla ila, long index, short value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() + 1;
        }

        @Override
        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
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
