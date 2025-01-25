package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaSegment {
    private ByteIlaSegment() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long start) {
        Argument.assertNotNull(ila, "ila");

        try {
            return create(ila, start, ila.length() - start);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not get ila length!", e);
        }
    }

    public static ByteIla create(ByteIla ila, long start, long length) {
        return new ByteIlaImpl(ila, start, length);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final ByteIla ila;
        private final long start;
        private final long length;

        private ByteIlaImpl(ByteIla ila, long start, long length) {
            Argument.assertNotNull(ila, "ila");
            Argument.assertNotLessThan(start, 0, "start");
            Argument.assertNotLessThan(length, 0, "length");
            try {
                Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length!", e);
            }

            this.ila = ila;
            this.start = start;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
