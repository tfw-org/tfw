package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaReverse {
    private BooleanIlaReverse() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, final boolean[] buffer) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotNull(buffer, "buffer");

        return new BooleanIlaImpl(ila, buffer);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final boolean[] buffer;

        private BooleanIlaImpl(BooleanIla ila, final boolean[] buffer) {
            this.ila = ila;
            this.buffer = buffer;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
            final StridedBooleanIla stridedBooleanIla = StridedBooleanIlaFromBooleanIla.create(ila, buffer.clone());

            stridedBooleanIla.get(array, offset + length - 1, -1, length() - (start + length), length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
