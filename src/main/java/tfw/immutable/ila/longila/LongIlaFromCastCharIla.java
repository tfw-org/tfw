package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

public final class LongIlaFromCastCharIla {
    private LongIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static LongIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new LongIlaImpl(charIla, bufferSize);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final CharIla charIla;
        private final int bufferSize;

        private LongIlaImpl(CharIla charIla, int bufferSize) {
            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return charIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            CharIlaIterator fi =
                    new CharIlaIterator(CharIlaSegment.create(charIla, start, length), new char[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (long) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
