package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaRemove {
    private ByteIlaRemove() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyByteIla(ila, index);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final long index;

        MyByteIla(ByteIla ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index <= start) {
                ila.toArray(array, offset, stride, start + 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, stride, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                ila.toArray(array, offset, stride, start, indexMinusStart);
                ila.toArray(array, offset + indexMinusStart * stride, stride, index + 1, length - indexMinusStart);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
