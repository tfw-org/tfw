package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaScalarMultiply {
    private LongIlaScalarMultiply() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila, long scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila, scalar);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;
        private final long scalar;

        MyLongIla(LongIla ila, long scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(long[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] *= scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
