package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaSegment {
    private ByteIlaSegment() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static ByteIla create(ByteIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyByteIla(ila, start, length);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ByteIla ila;
        private final long start;

        MyByteIla(ByteIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
