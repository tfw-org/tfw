package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class CharIlaFromCastLongIla {
    private CharIlaFromCastLongIla() {
        // non-instantiable class
    }

    public static CharIla create(LongIla longIla, int bufferSize) {
        return new CharIlaImpl(longIla, bufferSize);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final LongIla longIla;
        private final int bufferSize;

        private CharIlaImpl(LongIla longIla, int bufferSize) {
            Argument.assertNotNull(longIla, "longIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return longIla.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long start, int length) throws IOException {
            LongIlaIterator fi =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start, length), new long[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (char) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
