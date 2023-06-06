package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaSegment;

/**
 *
 * @immutables.types=numericnotdouble
 */
public final class FloatIlaFromCastDoubleIla {
    private FloatIlaFromCastDoubleIla() {
        // non-instantiable class
    }

    public static FloatIla create(DoubleIla doubleIla) {
        return create(doubleIla, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static FloatIla create(DoubleIla doubleIla, int bufferSize) {
        Argument.assertNotNull(doubleIla, "doubleIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyFloatIla(doubleIla, bufferSize);
    }

    private static class MyFloatIla extends AbstractFloatIla implements ImmutableProxy {
        private final DoubleIla doubleIla;
        private final int bufferSize;

        MyFloatIla(DoubleIla doubleIla, int bufferSize) {
            super(doubleIla.length());

            this.doubleIla = doubleIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator fi = new DoubleIlaIterator(DoubleIlaSegment.create(doubleIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (float) fi.next();
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "FloatIlaFromCastDoubleIla");
            map.put("doubleIla", getImmutableInfo(doubleIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
