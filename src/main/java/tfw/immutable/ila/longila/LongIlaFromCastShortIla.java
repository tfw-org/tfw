package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class LongIlaFromCastShortIla {
    private LongIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static LongIla create(ShortIla shortIla, int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyLongIla(shortIla, bufferSize);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyLongIla(ShortIla shortIla, int bufferSize) {
            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return shortIla.length();
        }

        @Override
        protected void toArrayImpl(long[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator fi =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
