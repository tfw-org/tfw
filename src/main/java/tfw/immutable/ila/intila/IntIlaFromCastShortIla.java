package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

/**
 *
 * @immutables.types=numericnotshort
 */
public final class IntIlaFromCastShortIla {
    private IntIlaFromCastShortIla() {
        // non-instantiable class
    }

    public static IntIla create(ShortIla shortIla) {
        return create(shortIla, ShortIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(ShortIla shortIla, int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(shortIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyIntIla(ShortIla shortIla, int bufferSize) {
            super(shortIla.length());

            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ShortIlaIterator fi = new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaFromCastShortIla");
            map.put("shortIla", getImmutableInfo(shortIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
