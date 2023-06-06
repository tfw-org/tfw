package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class CharIlaRamp {
    private CharIlaRamp() {
        // non-instantiable class
    }

    public static CharIla create(char startValue, char increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(startValue, increment, length);
    }

    private static class MyCharIla extends AbstractCharIla implements ImmutableProxy {
        private final char startValue;
        private final char increment;

        MyCharIla(char startValue, char increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // char value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            char value = (char) (startValue + increment * start);
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "CharIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Character(startValue));
            map.put("increment", new Character(increment));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
