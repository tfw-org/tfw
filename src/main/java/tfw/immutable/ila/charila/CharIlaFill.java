package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaFill {
    private CharIlaFill() {
        // non-instantiable class
    }

    public static CharIla create(char value, long length) {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyCharIla(value, length);
    }

    private static class MyCharIla extends AbstractCharIla implements ImmutableProxy {
        private final char value;

        MyCharIla(char value, long length) {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = value;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "CharIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Character(value));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
