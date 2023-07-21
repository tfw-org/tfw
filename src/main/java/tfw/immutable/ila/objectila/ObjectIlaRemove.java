package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaRemove {
    private ObjectIlaRemove() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyObjectIla<>(ila, index);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> ila;
        private final long index;

        MyObjectIla(ObjectIla<T> ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(T[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index <= start) {
                ila.toArray(array, offset, stride, start + 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, stride, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                ila.toArray(array, offset, stride, start, indexMinusStart);
                ila.toArray(array, offset + indexMinusStart * stride, stride, index + 1, length - indexMinusStart);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
