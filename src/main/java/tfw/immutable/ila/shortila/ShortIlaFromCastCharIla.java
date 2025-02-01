package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

public final class ShortIlaFromCastCharIla {
    private ShortIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static ShortIla create(CharIla charIla, int bufferSize) {
        return new ShortIlaImpl(charIla, bufferSize);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final CharIla charIla;
        private final int bufferSize;

        private ShortIlaImpl(CharIla charIla, int bufferSize) {
            Argument.assertNotNull(charIla, "charIla");
            Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return charIla.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            CharIlaIterator fi =
                    new CharIlaIterator(CharIlaSegment.create(charIla, start, length), new char[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (short) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
