package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ByteIlaDecimate {
    private ByteIlaDecimate() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long factor, byte[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new MyByteIla(ila, factor, buffer);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final long factor;
        private final byte[] buffer;

        MyByteIla(ByteIla ila, long factor, byte[] buffer) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ByteIla segment = ByteIlaSegment.create(ila, segmentStart, segmentLength);
            final ByteIlaIterator fi = new ByteIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
