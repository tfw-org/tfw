package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaRemove
{
    private ObjectIlaRemove() {}

    public static <T> ObjectIla<T> create(final ObjectIla<T> ila, final long index)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyObjectIla<>(ila, index);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final ObjectIla<T> ila;
        private final long index;

        private MyObjectIla(final ObjectIla<T> ila, final long index)
        {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        @Override
        protected void toArrayImpl(final T[] array, final int offset,
                                   final int stride, final long start, final int length)
            throws DataInvalidException
        {
            final long startPlusLength = start + length;

            if(index <= start)
            {
                ila.toArray(array, offset, stride, start + 1, length);
            }
            else if(index >= startPlusLength)
            {
                ila.toArray(array, offset, stride, start, length);
            }
            else
            {
                final int indexMinusStart = (int) (index - start);
                ila.toArray(array, offset, stride, start, indexMinusStart);
                ila.toArray(array, offset + indexMinusStart * stride,
                            stride, index + 1, length - indexMinusStart);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
