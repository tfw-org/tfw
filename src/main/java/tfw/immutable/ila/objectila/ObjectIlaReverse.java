package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaReverse
{
    private ObjectIlaReverse() {}

    public static <T> ObjectIla<T> create(ObjectIla<T> ila)
    {
        Argument.assertNotNull(ila, "ila");

        return new MyObjectIla<>(ila);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final ObjectIla<T> ila;

        private MyObjectIla(final ObjectIla<T> ila)
        {
            super(ila.length());
            this.ila = ila;
        }

        @Override
        protected void toArrayImpl(final T[] array, final int offset,
                                   final int stride, final long start, final int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset + (length - 1) * stride,
                        -stride, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
