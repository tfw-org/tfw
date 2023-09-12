package tfw.immutable.ila.byteila;

import tfw.check.Argument;

public final class ByteIlaFromArray {
    private ByteIlaFromArray() {
        // non-instantiable class
    }

    public static ByteIla create(byte[] array) {
        Argument.assertNotNull(array, "array");

        return new MyByteIla(array);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final byte[] array;

        MyByteIla(byte[] array) {
            this.array = array;
        }

        @Override
        protected long lengthImpl() {
            return array.length;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) {
            System.arraycopy(this.array, (int) start, array, offset, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
