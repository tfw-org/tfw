package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaRemove {
    private BooleanIlaRemove() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long index) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new MyBooleanIla(ila, index);
    }

    private static class MyBooleanIla extends AbstractBooleanIla implements ImmutableProxy {
        private final BooleanIla ila;
        private final long index;

        MyBooleanIla(BooleanIla ila, long index) {
            super(ila.length() - 1);
            this.ila = ila;
            this.index = index;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
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

            map.put("name", "BooleanIlaRemove");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));
            map.put("index", new Long(index));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
