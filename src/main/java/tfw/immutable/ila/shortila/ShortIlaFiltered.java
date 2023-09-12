package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

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

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla ila;
        private final ShortFilter filter;
        private final short[] buffer;

        private MyShortIla(ShortIla ila, ShortFilter filter, short[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            ShortIlaIterator oii = new ShortIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(short[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator oii = new ShortIlaIterator(ShortIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                short node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
