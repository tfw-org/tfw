package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaConcatenateUtil;

public final class DoubleIlaConcatenate {
    private DoubleIlaConcatenate() {}

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        return new DoubleIlaImpl(leftIla, rightIla);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla leftIla;
        private final DoubleIla rightIla;

        private DoubleIlaImpl(DoubleIla leftIla, DoubleIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
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
