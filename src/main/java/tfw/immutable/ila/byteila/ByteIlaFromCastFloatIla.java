package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

public final class ByteIlaFromCastFloatIla {
    private ByteIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static ByteIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(floatIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyByteIla(FloatIla floatIla, int bufferSize) {
            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return floatIla.length();
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            FloatIlaIterator fi =
                    new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), new float[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (byte) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
