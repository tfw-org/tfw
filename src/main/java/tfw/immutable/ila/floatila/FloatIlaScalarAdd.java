package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class FloatIlaScalarAdd {
    private FloatIlaScalarAdd() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, float scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyFloatIla(ila, scalar);
    }

    private static class MyFloatIla extends AbstractFloatIla implements ImmutableProxy {
        private final FloatIla ila;
        private final float scalar;

        MyFloatIla(FloatIla ila, float scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "FloatIlaScalarAdd");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Float(scalar));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
