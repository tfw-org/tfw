package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaFill {
    private LongIlaFill() {
        // non-instantiable class
    }

    public static LongIla create(long value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyLongIla(value, length);
    }

    private static class MyLongIla extends AbstractLongIla implements ImmutableProxy {
        private final long value;

        MyLongIla(long value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "LongIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Long(value));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
