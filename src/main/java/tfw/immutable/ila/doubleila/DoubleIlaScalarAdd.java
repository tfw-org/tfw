package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class DoubleIlaScalarAdd {
    private DoubleIlaScalarAdd() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, double scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila, scalar);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final DoubleIla ila;
        private final double scalar;

        MyDoubleIla(DoubleIla ila, double scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaScalarAdd");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Double(scalar));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
