package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaSegment;

public final class FloatIlaFromIntIla {
    private FloatIlaFromIntIla() {}

    public static FloatIla create(IntIla intIla) {
        Argument.assertNotNull(intIla, "intIla");

        return new MyFloatIla(intIla);
    }

    private static class MyFloatIla extends AbstractFloatIla implements ImmutableProxy {
        private IntIla intIla;

        MyFloatIla(IntIla intIla) {
            super(intIla.length());

            this.intIla = intIla;
        }

        protected void toArrayImpl(float[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            IntIlaIterator iii = new IntIlaIterator(IntIlaSegment.create(intIla, start, length));

            for (int i = 0; i < length; i++) {
                array[offset + (i * stride)] = Float.intBitsToFloat(iii.next());
            }
        }

        public Map<String, Object> getParameters() {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("name", "FloatIlaFromIntIla");
            map.put("intIla", getImmutableInfo(intIla));
            map.put("length", new Long(length()));

            return (map);
        }
    }
}
