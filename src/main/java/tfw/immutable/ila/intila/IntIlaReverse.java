package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaReverseUtil;

public final class IntIlaReverse {
    private IntIlaReverse() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, final int[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new IntIlaImpl(ila, buffer);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla ila;
        private final int[] buffer;

        private IntIlaImpl(IntIla ila, final int[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
            final int[] reverseBuffer = buffer.clone();

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
