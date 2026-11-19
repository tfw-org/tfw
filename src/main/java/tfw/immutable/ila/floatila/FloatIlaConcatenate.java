package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaConcatenateUtil;

public final class FloatIlaConcatenate {
    private FloatIlaConcatenate() {}

    public static FloatIla create(FloatIla leftIla, FloatIla rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        return new FloatIlaImpl(leftIla, rightIla);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla leftIla;
        private final FloatIla rightIla;

        private FloatIlaImpl(FloatIla leftIla, FloatIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
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
