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
public final class BooleanIlaReverse {
    private BooleanIlaReverse() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyBooleanIla(ila);
    }

    private static class MyBooleanIla extends AbstractBooleanIla implements ImmutableProxy {
        private final BooleanIla ila;

        MyBooleanIla(BooleanIla ila) {
            super(ila.length());
            this.ila = ila;
        }

        protected void toArrayImpl(boolean[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset + (length - 1) * stride, -stride, length() - (start + length), length);
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "BooleanIlaReverse");
            map.put("length", new Long(length()));
            map.put("ila", getImmutableInfo(ila));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
