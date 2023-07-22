package tfw.immutable.ila.charila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class CharIlaMultiply {
    private CharIlaMultiply() {
        // non-instantiable class
    }

    public static CharIla create(CharIla leftIla, CharIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(leftIla, rightIla, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla leftIla;
        private final CharIla rightIla;
        private final int bufferSize;

        MyCharIla(CharIla leftIla, CharIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long ilaStart, int length)
                throws DataInvalidException {
            CharIlaIterator li =
                    new CharIlaIterator(CharIlaSegment.create(leftIla, ilaStart, length), new char[bufferSize]);
            CharIlaIterator ri =
                    new CharIlaIterator(CharIlaSegment.create(rightIla, ilaStart, length), new char[bufferSize]);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (char) (li.next() * ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
