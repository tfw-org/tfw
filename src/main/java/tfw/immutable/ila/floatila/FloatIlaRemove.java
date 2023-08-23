package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaRemove {
    private FloatIlaRemove() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long index) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyFloatIla(ila, index);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final long index;

        MyFloatIla(FloatIla ila, long index) {
            this.ila = ila;
            this.index = index;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() - 1;
        }

        @Override
        protected void toArrayImpl(float[] array, int offset, long start, int length) throws IOException {
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
