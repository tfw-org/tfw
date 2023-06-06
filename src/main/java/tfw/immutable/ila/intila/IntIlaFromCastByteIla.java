package tfw.immutable.ila.intila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;

/**
 *
 * @immutables.types=numericnotbyte
 */
public final class IntIlaFromCastByteIla {
    private IntIlaFromCastByteIla() {
        // non-instantiable class
    }

    public static IntIla create(ByteIla byteIla) {
        return create(byteIla, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static IntIla create(ByteIla byteIla, int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyIntIla(byteIla, bufferSize);
    }

    private static class MyIntIla extends AbstractIntIla implements ImmutableProxy {
        private final ByteIla byteIla;
        private final int bufferSize;

        MyIntIla(ByteIla byteIla, int bufferSize) {
            super(byteIla.length());

            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(int[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator fi = new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (int) fi.next();
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "IntIlaFromCastByteIla");
            map.put("byteIla", getImmutableInfo(byteIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
