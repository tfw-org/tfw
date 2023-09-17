package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaScalarAdd {
    private ShortIlaScalarAdd() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila, short scalar) {
        Argument.assertNotNull(ila, "ila");

        return new ShortIlaImpl(ila, scalar);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ShortIla ila;
        private final short scalar;

        private ShortIlaImpl(ShortIla ila, short scalar) {
            this.ila = ila;
            this.scalar = scalar;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] += scalar;
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
