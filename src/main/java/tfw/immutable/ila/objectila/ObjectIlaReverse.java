package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaReverse {
    private ObjectIlaReverse() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyObjectIla<>(ila);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;

        MyObjectIla(ObjectIla<T> ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(T[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
