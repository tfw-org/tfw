package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class IntIlaDecimate {
    private IntIlaDecimate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long factor) {
        return create(ila, factor, IntIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(IntIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(ila, factor, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final IntIla ila;
        private final long factor;
        private final int bufferSize;

        MyIntIla(IntIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            final IntIla segment = IntIlaSegment.create(ila, segmentStart, segmentLength);
            final IntIlaIterator fi = new IntIlaIterator(segment, bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
                fi.skip(factor - 1);
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
