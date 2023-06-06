package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaScalarAdd {
    private IntIlaScalarAdd() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, int scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila, scalar);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final IntIla ila;
        private final int scalar;

        MyIntIla(IntIla ila, int scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] += scalar;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaScalarAdd");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Integer(scalar));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
