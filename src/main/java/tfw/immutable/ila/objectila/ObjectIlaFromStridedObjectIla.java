package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

public final class ObjectIlaFromStridedObjectIla<T> {
    private ObjectIlaFromStridedObjectIla() {}

    public static <T> ObjectIla<T> create(final StridedObjectIla<T> stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new ObjectIlaImpl<>(stridedIla);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final StridedObjectIla<T> stridedIla;

        private ObjectIlaImpl(final StridedObjectIla<T> stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final T[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
