package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaSegment;

/**
 *
 * @immutables.types=numericnotfloat
 */
public final class ByteIlaFromCastFloatIla {
    private ByteIlaFromCastFloatIla() {
        // non-instantiable class
    }

    public static ByteIla create(FloatIla floatIla) {
        return create(floatIla, FloatIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(FloatIla floatIla, int bufferSize) {
        Argument.assertNotNull(floatIla, "floatIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(floatIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private final FloatIla floatIla;
        private final int bufferSize;

        MyByteIla(FloatIla floatIla, int bufferSize) {
            super(floatIla.length());

            this.floatIla = floatIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            FloatIlaIterator fi = new FloatIlaIterator(FloatIlaSegment.create(floatIla, start, length), bufferSize);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] = (byte) fi.next();
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaFromCastFloatIla");
            map.put("floatIla", getImmutableInfo(floatIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
