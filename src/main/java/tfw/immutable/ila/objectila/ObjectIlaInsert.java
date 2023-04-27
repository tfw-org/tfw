package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaInsert
{
    private ObjectIlaInsert() {}

    public static <T> ObjectIla<T> create(final ObjectIla<T> ila, final long index, final T value)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertNotGreaterThan(index, ila.length(), "index",
                                      "ila.length()");

        return new MyObjectIla<>(ila, index, value);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final ObjectIla<T> ila;
        private final long index;
        private final T value;

        private MyObjectIla(final ObjectIla<T> ila, final long index, final T value)
        {
            super(ila.length() + 1);
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected void toArrayImpl(final T[] array, final int offset,
                                   final int stride, final long start, final int length)
            throws DataInvalidException
        {
            final long startPlusLength = start + length;

            if(index < start)
            {
                ila.toArray(array, offset, stride, start - 1, length);
            }
            else if(index >= startPlusLength)
            {
                ila.toArray(array, offset, stride, start, length);
            }
            else
            {
                final int indexMinusStart = (int) (index - start);
                if(index > start)
                {
                    ila.toArray(array, offset, stride, start,
                                indexMinusStart);
                }
                array[offset + indexMinusStart * stride] = value;
                if(index < startPlusLength - 1)
                {
                    ila.toArray(array, offset + (indexMinusStart + 1) * stride,
                                stride, index, length - indexMinusStart - 1);
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
