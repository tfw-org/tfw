package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaSegment;

public final class IntIlaFromCastCharIla {
    private IntIlaFromCastCharIla() {
        // non-instantiable class
    }

    public static IntIla create(CharIla charIla, int bufferSize) {
        Argument.assertNotNull(charIla, "charIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(charIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla {
        private final CharIla charIla;
        private final int bufferSize;

        MyIntIla(CharIla charIla, int bufferSize) {
            this.charIla = charIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return charIla.length();
        }

        @Override
        protected void toArrayImpl(int[] array, int offset, long start, int length) throws IOException {
            CharIlaIterator fi =
                    new CharIlaIterator(CharIlaSegment.create(charIla, start, length), new char[bufferSize]);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (int) fi.next();
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
