package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.AbstractIlaCheck;

public final class ShortIlaFiltered {
    private ShortIlaFiltered() {
        // non-instantiable class
    }

    public interface ShortFilter {
        boolean matches(short value);
    }

    public static ShortIla create(ShortIla ila, ShortFilter filter, short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyShortIla(ila, filter, buffer);
    }

    private static class MyShortIla implements ShortIla {
        private final ShortIla ila;
        private final ShortFilter filter;
        private final short[] buffer;

        private long length = -1;

        private MyShortIla(ShortIla ila, ShortFilter filter, short[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(short[] array, int offset, long start, int length) throws DataInvalidException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, start, length);

            ShortIlaIterator oii = new ShortIlaIterator(ShortIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                short node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                ShortIlaIterator oii = new ShortIlaIterator(ila, buffer.clone());

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
