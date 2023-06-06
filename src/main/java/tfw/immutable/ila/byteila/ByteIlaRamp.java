package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class ByteIlaRamp {
    private ByteIlaRamp() {
        // non-instantiable class
    }

    public static ByteIla create(byte startValue, byte increment, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyByteIla(startValue, increment, length);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private final byte startValue;
        private final byte increment;

        MyByteIla(byte startValue, byte increment, long length) {
            super(length);
            this.startValue = startValue;
            this.increment = increment;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);

            // CORRECT, BUT WAY TOO SLOW
            // byte value = startValue;
            // for(long ii = 0; ii < start; ++ii)
            // {
            //    value += increment;
            // }

            // INCORRECT, BUT FAST
            byte value = (byte) (startValue + increment * start);
            for (int startInt = (int) start;
                    startInt != startPlusLength;
                    ++startInt, offset += stride, value += increment) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaRamp");
            map.put("length", new Long(length()));
            map.put("startValue", new Byte(startValue));
            map.put("increment", new Byte(increment));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
