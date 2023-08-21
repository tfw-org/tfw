package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.AbstractIlaCheck;

public final class ObjectIlaFiltered<T> {
    private ObjectIlaFiltered() {
        // non-instantiable class
    }

    public interface ObjectFilter<T> {
        boolean matches(T value);
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, ObjectFilter<T> filter, T[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(filter, "filter");
        Argument.assertNotNull(buffer, "buffer");

        return new MyObjectIla<>(ila, filter, buffer);
    }

    private static class MyObjectIla<T> implements ObjectIla<T> {
        private final ObjectIla<T> ila;
        private final ObjectFilter<T> filter;
        private final T[] buffer;

        private long length = -1;

        private MyObjectIla(ObjectIla<T> ila, ObjectFilter<T> filter, T[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        public final long length() {
            calculateLength();

            return length;
        }

        public final void toArray(T[] array, int offset, long start, int length) throws IOException {
            calculateLength();

            if (length == 0) {
                return;
            }

            AbstractIlaCheck.boundsCheck(this.length, array.length, offset, start, length);

            ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ObjectIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                T node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }

        private void calculateLength() {
            if (length < 0) {
                length = ila.length();
                ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ila, buffer.clone());

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
