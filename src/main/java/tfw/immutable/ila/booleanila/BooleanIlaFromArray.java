package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaFromArray {
    private BooleanIlaFromArray() {
        // non-instantiable class
    }

    public static BooleanIla create(boolean[] array) {
        return create(array, true);
    }

    public static BooleanIla create(boolean[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyBooleanIla(array, cloneArray);
    }

    private static class MyBooleanIla extends AbstractBooleanIla implements ImmutableProxy {
        private final boolean[] array;

        MyBooleanIla(boolean[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (boolean[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "BooleanIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
