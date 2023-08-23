package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaBound {
    private FloatIlaBound() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, float minimum, float maximum) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyFloatIla(ila, minimum, maximum);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final FloatIla ila;
        private final float minimum;
        private final float maximum;

        MyFloatIla(FloatIla ila, float minimum, float maximum) {
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void toArrayImpl(float[] array, int offset, long start, int length) throws IOException {
            ila.toArray(array, offset, start, length);

            for (int ii = offset; length > 0; ii++, --length) {
                float tmp = array[ii];
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
