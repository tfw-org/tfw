package tfw.immutable.ila.doubleila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class DoubleIlaSubtract {
    private DoubleIlaSubtract() {
        // non-instantiable class
    }

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla) {
        return create(leftIla, rightIla, DoubleIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static DoubleIla create(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyDoubleIla(leftIla, rightIla, bufferSize);
    }

    private static class MyDoubleIla extends AbstractDoubleIla implements ImmutableProxy {
        private final DoubleIla leftIla;
        private final DoubleIla rightIla;
        private final int bufferSize;

        MyDoubleIla(DoubleIla leftIla, DoubleIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(double[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            DoubleIlaIterator li = new DoubleIlaIterator(DoubleIlaSegment.create(leftIla, start, length), bufferSize);
            DoubleIlaIterator ri = new DoubleIlaIterator(DoubleIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride) {
                array[ii] = (double) (li.next() - ri.next());
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "DoubleIlaSubtract");
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
            map.put("bufferSize", new Integer(bufferSize));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
