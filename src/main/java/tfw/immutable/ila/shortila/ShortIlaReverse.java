package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaReverseUtil;

public final class ShortIlaReverse {
    private ShortIlaReverse() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, final short[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");
        Argument.assertNotLessThan(buffer.length, 1, "buffer.length");

        return new ShortIlaImpl(ila, buffer);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ShortIla ila;
        private final short[] buffer;

        private ShortIlaImpl(ShortIla ila, final short[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            final short[] reverseBuffer = buffer.clone();

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
