package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaReverseUtil;

public final class LongIlaReverse {
    private LongIlaReverse() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, final long[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new LongIlaImpl(ila, buffer);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long[] buffer;

        private LongIlaImpl(LongIla ila, final long[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            final long[] reverseBuffer = buffer.clone();

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
