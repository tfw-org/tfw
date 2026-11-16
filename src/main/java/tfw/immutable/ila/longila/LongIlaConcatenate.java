package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaConcatenateUtil;

public final class LongIlaConcatenate {
    private LongIlaConcatenate() {}

    public static LongIla create(LongIla leftIla, LongIla rightIla) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");

        return new LongIlaImpl(leftIla, rightIla);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla leftIla;
        private final LongIla rightIla;

        private LongIlaImpl(LongIla leftIla, LongIla rightIla) {
            this.leftIla = leftIla;
            this.rightIla = rightIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return leftIla.length() + rightIla.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
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
