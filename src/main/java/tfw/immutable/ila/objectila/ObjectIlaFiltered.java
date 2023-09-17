package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

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

        return new ObjectIlaImpl<>(ila, filter, buffer);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final ObjectFilter<T> filter;
        private final T[] buffer;

        private ObjectIlaImpl(ObjectIla<T> ila, ObjectFilter<T> filter, T[] buffer) {
            this.ila = ila;
            this.filter = filter;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            long length = ila.length();
            ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ila, buffer.clone());

            while (oii.hasNext()) {
                if (filter.matches(oii.next())) {
                    length--;
                }
            }

            return length;
        }

        @Override
        public void getImpl(T[] array, int offset, long start, int length) throws IOException {
            ObjectIlaIterator<T> oii = new ObjectIlaIterator<>(ObjectIlaSegment.create(ila, start), buffer.clone());

            // left off here
            for (int i = offset; oii.hasNext(); i++) {
                T node = oii.next();

                if (!filter.matches(node)) {
                    array[i] = node;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
