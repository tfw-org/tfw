package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class ByteIlaScalarMultiply {
    private ByteIlaScalarMultiply() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla ila, byte scalar) {
        Argument.assertNotNull(ila, "ila");

        return new MyByteIla(ila, scalar);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private final ByteIla ila;
        private final byte scalar;

        MyByteIla(ByteIla ila, byte scalar) {
            super(ila.length());

            this.ila = ila;
            this.scalar = scalar;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length) {
                array[ii] *= scalar;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaScalarMultiply");
            map.put("ila", getImmutableInfo(ila));
            map.put("scalar", new Byte(scalar));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
