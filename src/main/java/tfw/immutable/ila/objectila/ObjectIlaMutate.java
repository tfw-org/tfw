package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaMutate {
    private ObjectIlaMutate() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long index, T value) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyObjectIla<>(ila, index, value);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final long index;
        private final T value;

        MyObjectIla(ObjectIla<T> ila, long index, T value) {
            super(ila.length());
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        protected void toArrayImpl(T[] array, int offset, long start, int length) throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index < start || index >= startPlusLength) {
                ila.toArray(array, offset, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                if (index > start) {
                    ila.toArray(array, offset, start, indexMinusStart);
                }
                array[offset + indexMinusStart] = value;
                if (index <= startPlusLength) {
                    ila.toArray(array, offset + (indexMinusStart + 1), index + 1, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
