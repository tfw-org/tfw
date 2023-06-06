package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaFill {
    private IntIlaFill() {
        // non-instantiable class
    }

    public static IntIla create(int value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyIntIla(value, length);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final int value;

        MyIntIla(int value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Integer(value));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
