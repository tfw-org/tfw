package tfw.immutable.ila.charila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class CharIlaSegment {
    private CharIlaSegment() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long start) {
        return create(ila, start, ila.length() - start);
    }

    public static CharIla create(CharIla ila, long start, long length) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(start, 0, "start");
        Argument.assertNotLessThan(length, 0, "length");
        Argument.assertNotGreaterThan((start + length), ila.length(), "start + length", "ila.length()");

        return new MyCharIla(ila, start, length);
    }

    private static class MyCharIla extends AbstractCharIla implements ImmutableProxy {
        private final CharIla ila;
        private final long start;

        MyCharIla(CharIla ila, long start, long length) {
            super(length);
            this.ila = ila;
            this.start = start;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, this.start + start, length);
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "CharIlaSegment");
            map.put("length", new Long(length()));
            map.put("start", new Long(start));
            map.put("ila", getImmutableInfo(ila));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
