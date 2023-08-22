package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaSubtract {
    private ShortIlaSubtract() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla leftIla, ShortIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyShortIla(leftIla, rightIla, bufferSize);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla leftIla;
        private final ShortIla rightIla;
        private final int bufferSize;

        MyShortIla(ShortIla leftIla, ShortIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(short[] array, int offset, long ilaStart, int length) throws IOException {
            ShortIlaIterator li =
                    new ShortIlaIterator(ShortIlaSegment.create(leftIla, ilaStart, length), new short[bufferSize]);
            ShortIlaIterator ri =
                    new ShortIlaIterator(ShortIlaSegment.create(rightIla, ilaStart, length), new short[bufferSize]);

            for (int ii = offset; li.hasNext(); ii++) {
                array[ii] = (short) (li.next() - ri.next());
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
