package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

/**
 *
 * @immutables.types=all
 */
public final class ObjectIlaConcatenate
{
    private ObjectIlaConcatenate() {}

    public static <T> ObjectIla<T> create(final ObjectIla<T> leftIla, final ObjectIla<T> rightIla)
    {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        if (leftIla.length() == 0) {
            return rightIla;
        }
        if (rightIla.length() == 0) {
            return leftIla;
        }

        return new MyObjectIla<>(leftIla, rightIla);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T>
    {
        private final ObjectIla<T> leftIla;
        private final ObjectIla<T> rightIla;
        private final long leftIlaLength;

        private MyObjectIla(final ObjectIla<T> leftIla, final ObjectIla<T> rightIla)
        {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        @Override
        protected void toArrayImpl(final T[] array, final int offset,
                                   final int stride, final long start, final int length)
            throws DataInvalidException
        {
            if(start + length <= leftIlaLength)
            {
                leftIla.toArray(array, offset, stride, start, length);
            }
            else if(start >= leftIlaLength)
            {
                rightIla.toArray(array, offset, stride, start - leftIlaLength,
                                 length);
            }
            else
            {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, stride, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount * stride,
                                 stride, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
