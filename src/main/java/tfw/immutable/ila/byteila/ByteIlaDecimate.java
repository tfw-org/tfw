package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaDecimate {
    private ByteIlaDecimate() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long factor) {
        return create(ila, factor, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(ByteIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(ila, factor, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final long factor;
        private final int bufferSize;

        MyByteIla(ByteIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ByteIla segment = ByteIlaSegment.create(ila, segmentStart, segmentLength);
            final ByteIlaIterator fi = new ByteIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
