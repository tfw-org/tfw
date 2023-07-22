package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaSegment {
    private ObjectIlaSegment() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan(start + length, ila.length(), "start + length", "ila.length()");

        return new MyObjectIla<>(ila, start, length);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final long start;

        MyObjectIla(ObjectIla<T> ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(T[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
