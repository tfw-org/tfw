package tfw.immutable.ila.byteila;

import tfw.check.Argument;

public final class ByteIlaFill {
    private ByteIlaFill() {
        // non-instantiable class
    }

    public static ByteIla create(byte value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyByteIla(value, length);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final byte value;
        private final long length;

        MyByteIla(byte value, long length) {
            this.value = value;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void toArrayImpl(byte[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
