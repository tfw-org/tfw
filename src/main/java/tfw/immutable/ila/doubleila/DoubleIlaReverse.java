package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaReverse {
    private DoubleIlaReverse() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyDoubleIla(ila);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final DoubleIla ila;

        MyDoubleIla(DoubleIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
