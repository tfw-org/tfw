package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class LongIlaFromCastByteIla {
    private LongIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static LongIla create(ByteIla byteIla, int bufferSize) {
        return new LongIlaImpl(byteIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private LongIlaImpl(ByteIla byteIla, int bufferSize) {
            Argument.assertNotNull(byteIla, "byteIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator fi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
