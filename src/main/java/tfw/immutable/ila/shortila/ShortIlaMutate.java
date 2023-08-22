package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaMutate {
    private ShortIlaMutate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, long index, short value) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyShortIla(ila, index, value);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final long index;
        private final short value;

        MyShortIla(ShortIla ila, long index, short value) {
            super(ila.length());
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(short[] array, int offset, long start, int length) throws IOException {
            final long startPlusLength = start + length;

            if (index < start || index >= startPlusLength) {
                ila.toArray(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.toArray(array, offset, start, indexMinusStart);
                }
                array[offset + indexMinusStart] = value;
                if (index <= startPlusLength) {
                    ila.toArray(array, offset + (indexMinusStart + 1), index + 1, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
