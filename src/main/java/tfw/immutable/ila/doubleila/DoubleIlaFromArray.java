package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaFromArray {
    private DoubleIlaFromArray() {
        // non-instantiable class
    }

    public static DoubleIla create(double[] array) {
        return create(array, true);
    }

    public static DoubleIla create(double[] array, boolean cloneArray) {
        Argument.assertNotNull(array, "array");

        return new MyDoubleIla(array, cloneArray);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final double[] array;

        MyDoubleIla(double[] array, boolean cloneArray) {
            super(array.length);

            if (cloneArray) {
                this.array = (double[]) array.clone();
            } else {
                this.array = array;
            }
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length) {
            final int startPlusLength = (int) (start + length);
            for (int startInt = (int) start; startInt != startPlusLength; ++startInt, offset += stride) {
                array[offset] = this.array[startInt];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaFromArray");
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
