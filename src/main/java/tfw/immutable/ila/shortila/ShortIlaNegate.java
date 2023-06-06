package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class ShortIlaNegate {
    private ShortIlaNegate() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyShortIla(ila);
    }

    private static class MyShortIla extends AbstractShortIla implements ImmutableProxy {
        private final ShortIla ila;

        MyShortIla(ShortIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (short) -array[ii];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ShortIlaNegate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
