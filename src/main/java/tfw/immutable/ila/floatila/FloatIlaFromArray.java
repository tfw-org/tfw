package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaFromArray {
    private FloatIlaFromArray() {
        // non-instantiable class
    }

    public static FloatIla create(float[] array) {
        return create(array, true);
    }

    public static FloatIla create(float[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyFloatIla(array, cloneArray);
    }

    private static class MyFloatIla extends AbstractFloatIla implements ImmutableProxy {
        private final float[] array;

        MyFloatIla(float[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (float[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "FloatIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
