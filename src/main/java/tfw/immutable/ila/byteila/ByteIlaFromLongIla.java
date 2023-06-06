package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaIterator;
import tfw.immutable.ila.longila.LongIlaSegment;

public final class ByteIlaFromLongIla {
    private ByteIlaFromLongIla() {}

    public static ByteIla create(LongIla longIla) {
        Argument.assertNotNull(longIla, "longIla");

        return new MyByteIla(longIla);
    }

    private static class MyByteIla extends AbstractByteIla implements ImmutableProxy {
        private LongIla longIla;

        MyByteIla(LongIla longIla) {
            super(longIla.length() * 8);

            this.longIla = longIla;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            LongIlaIterator lii =
                    new LongIlaIterator(LongIlaSegment.create(longIla, start / 8, longIla.length() - start / 8));
            int col = (int) (start % 8);

            for (int totalElements = 0; totalElements < length; ) {
                long value = lii.next();

                for (int c = col; c < 8 && totalElements < length; c++) {
                    array[offset + (totalElements++ * stride)] = (byte) ((value >>> (56 - (8 * c))) & 0xFF);
                }

                col = 0;
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "ByteIlaFromLongIla");
            map.put("longIla", getImmutableInfo(longIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
