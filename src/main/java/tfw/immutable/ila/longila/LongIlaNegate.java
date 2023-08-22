package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaNegate {
    private LongIlaNegate() {
        // non-instantiable class
    }

    public static LongIla create(LongIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyLongIla(ila);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final LongIla ila;

        MyLongIla(LongIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(long[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
