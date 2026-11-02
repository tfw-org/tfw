package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaReverseUtil;

public final class DoubleIlaReverse {
    private DoubleIlaReverse() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, final double[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new DoubleIlaImpl(ila, buffer);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final DoubleIla ila;
        private final double[] buffer;

        private DoubleIlaImpl(DoubleIla ila, final double[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(double[] array, int offset, long start, int length) throws IOException {
            final double[] reverseBuffer = buffer.clone();

            IlaReverseUtil.reverse(
                    ila.length(),
                    offset,
                    start,
                    length,
                    reverseBuffer.length,
                    (sourcePosition, destinationOffset, amount) -> {
                        ila.get(reverseBuffer, 0, sourcePosition, amount);

                        for (int i = 0; i < amount; i++) {
                            array[destinationOffset + i] = reverseBuffer[amount - 1 - i];
                        }
                    });
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
