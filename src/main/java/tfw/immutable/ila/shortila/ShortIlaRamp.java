package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class ShortIlaRamp {
    private ShortIlaRamp() {
        // non-instantiable class
    }

    public static ShortIla create(short startValue, short increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyShortIla(startValue, increment, length);
    }

    private static class MyShortIla extends AbstractShortIla implements ImmutableProxy {
        private final short startValue;
        private final short increment;

        MyShortIla(short startValue, short increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // short value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            short value = (short) (startValue + increment * start);
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ShortIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Short(startValue));
            map.put("increment", new Short(increment));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
