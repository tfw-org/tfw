package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaMutateUtil;

public final class FloatIlaMutate {
    private FloatIlaMutate() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long index, float value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new FloatIlaImpl(ila, index, value);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final long index;
        private final float value;

        private FloatIlaImpl(FloatIla ila, long index, float value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            IlaMutateUtil.get(
                    index,
                    start,
                    length,
                    offset,
                    (destinationOffset, sourceStart, amount) -> ila.get(array, destinationOffset, sourceStart, amount),
                    destinationOffset -> array[destinationOffset] = value);
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
