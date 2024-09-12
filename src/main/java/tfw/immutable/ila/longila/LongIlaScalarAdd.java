package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaScalarAdd {
    private LongIlaScalarAdd() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long scalar) {
        Argument.assertNotNull(ila, "ila");

        return new LongIlaImpl(ila, scalar);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long scalar;

        private LongIlaImpl(LongIla ila, long scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
