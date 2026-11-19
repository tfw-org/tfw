package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaConcatenateUtil;

public final class BooleanIlaConcatenate {
    private BooleanIlaConcatenate() {}

    public static BooleanIla create(BooleanIla leftIla, BooleanIla rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        return new BooleanIlaImpl(leftIla, rightIla);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla leftIla;
        private final BooleanIla rightIla;

        private BooleanIlaImpl(BooleanIla leftIla, BooleanIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
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
