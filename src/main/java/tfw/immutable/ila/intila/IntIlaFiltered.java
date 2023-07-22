package tfw.immutable.ila.intila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

public final class IntIlaFiltered {
    private IntIlaFiltered() {
        // non-instantiable class
    }

    public interface IntFilter {
        boolean matches(int value);
    }

    public static IntIla create(IntIla ila, IntFilter filter, int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyIntIla(ila, filter, buffer);
    }

    private static class MyIntIla implements IntIla {
        private final IntIla ila;
        private final IntFilter filter;
        private final int[] buffer;

        private long length = -1;

        private MyIntIla(IntIla ila, IntFilter filter, int[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(int[] array, int offset, long start, int length) throws DataInvalidException {
            toArray(array, offset, 1, start, length);
        }

        public final void toArray(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, stride, start, length);

            IntIlaIterator oii = new IntIlaIterator(IntIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i += stride) {
                int node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                IntIlaIterator oii = new IntIlaIterator(ila, buffer.clone());

                try {
                    while (oii.hasNext()) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                } catch (DataInvalidException die) {
                    length = 0;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
