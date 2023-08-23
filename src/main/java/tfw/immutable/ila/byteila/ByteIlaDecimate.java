package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

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
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void toArrayImpl(byte[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final ByteIla segment = ByteIlaSegment.create(ila, segmentStart, segmentLength);
            final ByteIlaIterator fi = new ByteIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
