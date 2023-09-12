package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaDivide {
    private CharIlaDivide() {
        // non-instantiable class
    }

    public static CharIla create(CharIla leftIla, CharIla rightIla, int bufferSize) throws IOException {
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
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length();
        }

        @Override
        protected void getImpl(char[] array, int offset, long ilaStart, int length) throws IOException {
            CharIlaIterator li =
                    new CharIlaIterator(CharIlaSegment.create(leftIla, ilaStart, length), new char[bufferSize]);
            CharIlaIterator ri =
                    new CharIlaIterator(CharIlaSegment.create(rightIla, ilaStart, length), new char[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = (char) (li.next() / ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
