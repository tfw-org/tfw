package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class DoubleIlaDecimate {
    private DoubleIlaDecimate() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla ila, long factor) {
        return create(ila, factor, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(DoubleIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(ila, factor, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final DoubleIla ila;
        private final long factor;
        private final int bufferSize;

        MyDoubleIla(DoubleIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            DoubleIlaIterator fi =
                    new DoubleIlaIterator(DoubleIlaSegment.create(ila, segmentStart, segmentLength), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (double) fi.next();
                fi.skip(factor - 1);
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
