package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaFiltered {
    private CharIlaFiltered() {
        // non-instantiable class
    }

    public interface CharFilter {
        boolean matches(char value);
    }

    public static CharIla create(CharIla ila, CharFilter filter, char[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyCharIla(ila, filter, buffer);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final CharIla ila;
        private final CharFilter filter;
        private final char[] buffer;

        private MyCharIla(CharIla ila, CharFilter filter, char[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            CharIlaIterator oii = new CharIlaIterator(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
            CharIlaIterator oii = new CharIlaIterator(CharIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                char node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
