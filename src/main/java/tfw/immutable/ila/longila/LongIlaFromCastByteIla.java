package tfw.immutable.ila.longila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

/**
 *
 * @immutables.types=numericnotbyte
 */
public final class LongIlaFromCastByteIla {
    private LongIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static LongIla create(ByteIla byteIla) {
        return create(byteIla, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static LongIla create(ByteIla byteIla, int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(byteIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyLongIla(ByteIla byteIla, int bufferSize) {
            super(byteIla.length());

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator fi = new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
