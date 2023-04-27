package tfw.immutable.ila.objectila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFill
{
    private ObjectIlaFill() {}

    public static <T> ObjectIla<T> create(final T value, final long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyObjectIla<>(value, length);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final T value;

        private MyObjectIla(final T value, final long length)
        {
            super(length);
            
            this.value = value;
        }

        @Override
        protected void toArrayImpl(final T[] array, int offset,
                                   final int stride, final long start, final int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = value;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
