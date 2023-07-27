package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaReverse {
    private ObjectIlaReverse() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, final T[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new MyObjectIla<>(ila, buffer);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final T[] buffer;

        MyObjectIla(ObjectIla<T> ila, final T[] buffer) {
            super(ila.length());

            this.ila = ila;
            this.buffer = buffer;
        }

        protected void toArrayImpl(T[] array, int offset, long start, int length) throws DataInvalidException {
            final StridedObjectIla<T> stridedObjectIla = new StridedObjectIla<>(ila, buffer.clone());

            stridedObjectIla.toArray(array, offset + (length - 1), -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
