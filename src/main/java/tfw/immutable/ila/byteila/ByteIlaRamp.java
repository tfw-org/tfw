package tfw.immutable.ila.byteila;

import tfw.check.Argument;

public final class ByteIlaRamp {
    private ByteIlaRamp() {
        // non-instantiable class
    }

    public static ByteIla create(byte startValue, byte increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyByteIla(startValue, increment, length);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final byte startValue;
        private final byte increment;
        private final long length;

        MyByteIla(byte startValue, byte increment, long length) {
            this.startValue = startValue;
            this.increment = increment;
            this.length = length;
        }

        @Override
        protected long lengthImpl() {
            return length;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // byte value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            byte value = (byte) (startValue + increment * start);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset++, value += increment) {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
