package tfw.immutable.stream;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import tfw.immutable.ila.objectila.ObjectIla;

public final class StreamFromObjectIla {
    private StreamFromObjectIla() {}

    public static <T> Stream<T> create(final ObjectIla<T> ila, Class<T> clazz) throws IOException {
        return StreamSupport.stream(new ObjectIlaSpliterator<T>(ila, clazz), false);
    }

    private static class ObjectIlaSpliterator<T> extends AbstractStreamFromIla<T> {
        private final ObjectIla<T> ila;
        private final T[] array;

        private int position = 0;

        @SuppressWarnings("unchecked")
        public ObjectIlaSpliterator(final ObjectIla<T> ila, Class<T> clazz) throws IOException {
            super(ila.length());

            this.ila = ila;
            array = (T[]) Array.newInstance(clazz, 1);
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            try {
                if (position < ila.length()) {
                    ila.get(array, 0, position++, 1);
                    action.accept(array[0]);

                    return true;
                }
            } catch (IOException e) {
                // do nothing and return false.
            }

            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }
    }
}
