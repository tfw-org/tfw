package tfw.immutable.ila.objectila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ObjectIlaConcatenate {
    private ObjectIlaConcatenate() {
        // non-instantiable class
    }

    public static <T> ObjectIla<T> create(ObjectIla<T> leftIla, ObjectIla<T> rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        /*
          // this efficiency step would help out in a number
          // of situations, but could be confusing when the
          // immutable proxy getParameters() is called and you
          // don't see your concatenation!
        if(leftIla.length() == 0)
            return rightIla;
        if(rightIla.length() == 0)
            return leftIla;
        */
        return new MyObjectIla<>(leftIla, rightIla);
    }

    private static class MyObjectIla<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> leftIla;
        private final ObjectIla<T> rightIla;
        private final long leftIlaLength;

        MyObjectIla(ObjectIla<T> leftIla, ObjectIla<T> rightIla) {
            super(leftIla.length() + rightIla.length());
            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.leftIlaLength = leftIla.length();
        }

        protected void toArrayImpl(T[] array, int offset, long start, int length) throws DataInvalidException {
            if (start + length <= leftIlaLength) {
                leftIla.toArray(array, offset, start, length);
            } else if (start >= leftIlaLength) {
                rightIla.toArray(array, offset, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.toArray(array, offset, start, leftAmount);
                rightIla.toArray(array, offset + leftAmount, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
