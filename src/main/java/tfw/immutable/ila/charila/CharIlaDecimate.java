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
public final class CharIlaDecimate {
    private CharIlaDecimate() {
        // non-instantiable class
    }

    public static CharIla create(CharIla ila, long factor) {
        return create(ila, factor, CharIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static CharIla create(CharIla ila, long factor, int bufferSize) {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(factor, 2, "factor");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyCharIla(ila, factor, bufferSize);
    }

    private static class MyCharIla extends AbstractCharIla implements ImmutableProxy {
        private final CharIla ila;
        private final long factor;
        private final int bufferSize;

        MyCharIla(CharIla ila, long factor, int bufferSize) {
            super((ila.length() + factor - 1) / factor);
            this.ila = ila;
            this.factor = factor;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(char[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            final long segmentStart = start * factor;
            final long segmentLength = StrictMath.min(ila.length() - segmentStart, length * factor - 1);
            CharIlaIterator fi =
                    new CharIlaIterator(CharIlaSegment.create(ila, segmentStart, segmentLength), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (char) fi.next();
                fi.skip(factor - 1);
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "CharIlaDecimate");
            map.put("ila", getImmutableInfo(ila));
            map.put("length", new Long(length()));
            map.put("factor", new Long(factor));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
