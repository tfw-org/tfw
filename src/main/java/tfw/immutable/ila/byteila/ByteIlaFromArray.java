package tfw.immutable.ila.byteila;

import tfw.check.Argument;

public final class ByteIlaFromArray {
    private ByteIlaFromArray() {
        // non-instantiable class
    }

    public static ByteIla create(byte[] array) {
        return create(array, true);
    }

    public static ByteIla create(byte[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyByteIla(array, cloneArray);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final byte[] array;

        MyByteIla(byte[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
