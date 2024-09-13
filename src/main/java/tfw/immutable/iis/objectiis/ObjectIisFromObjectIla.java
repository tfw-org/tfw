package tfw.immutable.iis.objectiis;

import java.io.IOException;
import tfw.immutable.ila.objectila.ObjectIla;

public final class ObjectIisFromObjectIla {
    private ObjectIisFromObjectIla() {}

    public static <T> ObjectIis<T> create(final ObjectIla<T> ila) {
        return new ObjectIisImpl<>(ila);
    }

    private static class ObjectIisImpl<T> extends AbstractObjectIis<T> {
        private final ObjectIla<T> ila;

        private long index = 0;

        public ObjectIisImpl(final ObjectIla<T> ila) {
            this.ila = ila;
        }

        @Override
        public void closeImpl() throws IOException {
            index = ila.length();
        }

        @Override
        protected int readImpl(T[] array, int offset, int length) throws IOException {
            final int elementsToGet = (int) Math.min(ila.length() - index, length);

            ila.get(array, offset, index, elementsToGet);

            return elementsToGet;
        }

        @Override
        protected long skipImpl(long n) throws IOException {
            final long originalIndex = index;

            index = Math.min(ila.length(), index + n);

            return index - originalIndex;
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
