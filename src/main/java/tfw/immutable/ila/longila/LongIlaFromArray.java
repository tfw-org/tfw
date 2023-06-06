package tfw.immutable.ila.longila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class LongIlaFromArray {
    private LongIlaFromArray() {
        // non-instantiable class
    }

    public static LongIla create(long[] array) {
        return create(array, true);
    }

    public static LongIla create(long[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyLongIla(array, cloneArray);
    }

    private static class MyLongIla extends AbstractLongIla implements ImmutableProxy {
        private final long[] array;

        MyLongIla(long[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (long[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(long[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "LongIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
