package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIlaCheck;

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

    private static class MyCharIla implements CharIla {
        private final CharIla ila;
        private final CharFilter filter;
        private final char[] buffer;

        private long length = -1;

        private MyCharIla(CharIla ila, CharFilter filter, char[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(char[] array, int offset, long start, int length) throws IOException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, start, length);

            CharIlaIterator oii = new CharIlaIterator(CharIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                char node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                CharIlaIterator oii = new CharIlaIterator(ila, buffer.clone());

                try {
                    while (oii.hasNext()) {
                        if (filter.matches(oii.next())) {
                            length--;
                        }
                    }
                } catch (IOException die) {
                    length = 0;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
