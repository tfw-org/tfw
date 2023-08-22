package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaMutate {
    private DoubleIlaMutate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long index, double value) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyDoubleIla(ila, index, value);
    }

    private static class MyDoubleIla extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final long index;
        private final double value;

        MyDoubleIla(DoubleIla ila, long index, double value) {
            super(ila.length());
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(double[] array, int offset, long start, int length) throws IOException {
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
