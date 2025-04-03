package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class IntIlaFromCastShortIla {
    private IntIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static IntIla create(ShortIla shortIla, int bufferSize) {
        return new IntIlaImpl(shortIla, bufferSize);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        private IntIlaImpl(ShortIla shortIla, int bufferSize) {
            Argument.assertNotNull(shortIla, "shortIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return shortIla.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator fi =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
