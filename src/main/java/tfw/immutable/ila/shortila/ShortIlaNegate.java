package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaNegate {
    private ShortIlaNegate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new ShortIlaImpl(ila);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ShortIla ila;

        private ShortIlaImpl(ShortIla ila) {
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (short) -array[ii];
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
