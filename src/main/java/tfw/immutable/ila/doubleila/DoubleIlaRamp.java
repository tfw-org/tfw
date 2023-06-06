package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class DoubleIlaRamp {
    private DoubleIlaRamp() {
        // non-instantiable class
    }

    public static DoubleIla create(double startValue, double increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyDoubleIla(startValue, increment, length);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final double startValue;
        private final double increment;

        MyDoubleIla(double startValue, double increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // double value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            double value = (double) (startValue + increment * start);
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Double(startValue));
            map.put("increment", new Double(increment));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
