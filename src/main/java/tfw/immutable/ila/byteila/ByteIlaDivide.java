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
public final class ByteIlaDivide {
    private ByteIlaDivide() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla) {
        return create(leftIla, rightIla, ByteIlaIterator.DEFAULT_BUFFER_SIZE);
    }

    public static ByteIla create(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
        Argument.assertNotNull(leftIla, "leftIla");
        Argument.assertNotNull(rightIla, "rightIla");
        Argument.assertEquals(leftIla.length(), rightIla.length(), "leftIla.length()", "rightIla.length()");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(leftIla, rightIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private final ByteIla leftIla;
        private final ByteIla rightIla;
        private final int bufferSize;

        MyByteIla(ByteIla leftIla, ByteIla rightIla, int bufferSize) {
            super(leftIla.length());

            this.leftIla = leftIla;
            this.rightIla = rightIla;
            this.bufferSize = bufferSize;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ByteIlaIterator li = new ByteIlaIterator(ByteIlaSegment.create(leftIla, start, length), bufferSize);
            ByteIlaIterator ri = new ByteIlaIterator(ByteIlaSegment.create(rightIla, start, length), bufferSize);

            for (int ii = offset; li.hasNext(); ii += stride) {
                array[ii] = (byte) (li.next() / ri.next());
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaDivide");
            map.put("leftIla", getImmutableInfo(leftIla));
            map.put("rightIla", getImmutableInfo(rightIla));
            map.put("bufferSize", new Integer(bufferSize));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
