package tfw.immutable.ila.objectila;

import tfw.check.Argument;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaFromArray
{
    private ObjectIlaFromArray() {}

    public static <T> ObjectIla<T> create(final T[] array)
    {
        Argument.assertNotNull(array, "array");

        return new MyObjectIla<>(array);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final T[] array;

        private MyObjectIla(final T[] array)
        {
            super(array.length);

            this.array = array;
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
                array[offset] = this.array[startInt];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
