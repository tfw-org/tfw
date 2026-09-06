package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaInsert {
    private FloatIlaInsert() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long index, float value) {
        return new FloatIlaImpl(ila, index, value);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final long index;
        private final float value;

        private FloatIlaImpl(FloatIla ila, long index, float value) {
            Argument.assertNotNull(ila, "ila");
            Argument.assertNotLessThan(index, 0, "index");
            try {
                Argument.assertNotGreaterThan(index, ila.length(), "index", "ila.length()");
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length()!", e);
            }

            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() + 1;
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
            IlaInsertUtil.get(
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
