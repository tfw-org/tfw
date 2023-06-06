package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class IntIlaNegate {
    private IntIlaNegate() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila) {
        Argument.assertNotNull(ila, "ila");

        return new MyIntIla(ila);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final IntIla ila;

        MyIntIla(IntIla ila) {
            super(ila.length());

            this.ila = ila;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) -array[ii];
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaNegate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
