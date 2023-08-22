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
        Argument.assertNotNull(longIla, "longIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(longIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final LongIla longIla;
        private final int bufferSize;

        MyCharIla(LongIla longIla, int bufferSize) {
            super(longIla.length());

            this.longIla = longIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            LongIlaIterator fi =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start, length), new long[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (char) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
