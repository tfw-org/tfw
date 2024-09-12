package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;

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
        return new ObjectIlaImpl<>(leftIla, rightIla);
    }

    private static class ObjectIlaImpl<T> extends AbstractObjectIla<T> {
        private final ObjectIla<T> leftIla;
        private final ObjectIla<T> rightIla;

        private ObjectIlaImpl(ObjectIla<T> leftIla, ObjectIla<T> rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(T[] array, int offset, long start, int length) throws IOException {
            final long leftIlaLength = leftIla.length();
            final long leftIlaLastIndex = leftIlaLength - 1;

            if (start + length <= leftIlaLastIndex) {
                leftIla.get(array, offset, start, length);
            } else if (start > leftIlaLastIndex) {
                rightIla.get(array, offset, start - leftIlaLength, length);
            } else {
                final int leftAmount = (int) (leftIlaLength - start);
                leftIla.get(array, offset, start, leftAmount);
                rightIla.get(array, offset + leftAmount, 0, length - leftAmount);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
