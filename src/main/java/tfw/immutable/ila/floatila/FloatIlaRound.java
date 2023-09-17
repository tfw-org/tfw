package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaRound {
    private FloatIlaRound() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new FloatIlaImpl(ila);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;

        private FloatIlaImpl(FloatIla ila) {
            this.ila = ila;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            ila.get(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                array[ii] = (float) StrictMath.rint(array[ii]);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
