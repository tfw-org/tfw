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
public final class IntIlaRemove {
    private IntIlaRemove() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyIntIla(ila, index);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final IntIla ila;
        private final long index;

        MyIntIla(IntIla ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long startPlusLength = start + length;

            if (index <= start) {
                ila.toArray(array, offset, stride, start + 1, length);
            } else if (index >= startPlusLength) {
                ila.toArray(array, offset, stride, start, length);
            } else {
                final int indexMinusStart = (int) (index - start);
                ila.toArray(array, offset, stride, start, indexMinusStart);
                ila.toArray(array, offset + indexMinusStart * stride, stride, index + 1, length - indexMinusStart);
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaRemove");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
            map.put("index", new Long(index));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
