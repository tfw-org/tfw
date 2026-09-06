package tfw.immutable.ila.objectila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaConcatenateUtil;

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

            IlaConcatenateUtil.get(
                    leftIlaLength,
                    offset,
                    start,
                    length,
                    (destinationOffset, sourceStart, amount) ->
                            leftIla.get(array, destinationOffset, sourceStart, amount),
                    (destinationOffset, sourceStart, amount) ->
                            rightIla.get(array, destinationOffset, sourceStart, amount));
        }

        @Override
        protected void closeImpl() throws IOException {
            leftIla.close();
            rightIla.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
