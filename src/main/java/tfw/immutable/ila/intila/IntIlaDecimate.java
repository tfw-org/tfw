package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaDecimate {
    private IntIlaDecimate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long factor, int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new IntIlaImpl(ila, factor, buffer);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla ila;
        private final long factor;
        private final int[] buffer;

        private IntIlaImpl(IntIla ila, long factor, int[] buffer) {
            this.ila = ila;
            this.factor = factor;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return (ila.length() + factor - 1) / factor;
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final IntIla segment = IntIlaSegment.create(ila, segmentStart, segmentLength);
            final IntIlaIterator fi = new IntIlaIterator(segment, buffer.clone());

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = fi.next();
                fi.skip(factor - 1);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
