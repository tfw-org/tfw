package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

public final class IntIlaFromCastByteIla {
    private IntIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static IntIla create(ByteIla byteIla, int bufferSize) {
        return new IntIlaImpl(byteIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private IntIlaImpl(ByteIla byteIla, int bufferSize) {
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
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator fi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
