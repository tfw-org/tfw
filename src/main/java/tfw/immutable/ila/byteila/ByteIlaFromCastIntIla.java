package tfw.immutable.ila.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

/**
 *
 * @immutables.types=numericnotint
 */
public final class ByteIlaFromCastIntIla {
    private ByteIlaFromCastIntIla() {
        // non-instantiable class
    }

    public static ByteIla create(IntIla intIla) {
        return create(intIla, IntIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(IntIla intIla, int bufferSize) {
        Argument.assertNotNull(intIla, "intIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(intIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final IntIla intIla;
        private final int bufferSize;

        MyByteIla(IntIla intIla, int bufferSize) {
            super(intIla.length());

            this.intIla = intIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator fi = new IntIlaIterator(IntIlaSegment.create(intIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
