package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaBound {
    private LongIlaBound() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long minimum, long maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new LongIlaImpl(ila, minimum, maximum);
    }

    private static class LongIlaImpl extends AbstractLongIla {
        private final LongIla ila;
        private final long minimum;
        private final long maximum;

        private LongIlaImpl(LongIla ila, long minimum, long maximum) {
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                long tmp = array[ii];
                if (tmp < minimum) {
                    array[ii] = minimum;
                } else if (tmp > maximum) {
                    array[ii] = maximum;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
