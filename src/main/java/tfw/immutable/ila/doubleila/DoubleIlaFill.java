package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaFill {
    private DoubleIlaFill() {
        // non-instantiable class
    }

    public static DoubleIla create(double value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyDoubleIla(value, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final double value;

        MyDoubleIla(double value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Double(value));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
