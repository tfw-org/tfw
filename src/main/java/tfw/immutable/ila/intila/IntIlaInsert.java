package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaInsert {
    private IntIlaInsert() {
        // non-instantiable class
    }

    public static IntIla create(IntIla ila, long index, int value) {
        return new IntIlaImpl(ila, index, value);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final IntIla ila;
        private final long index;
        private final int value;

        private IntIlaImpl(IntIla ila, long index, int value) {
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
        protected void getImpl(int[] array, int offset, long start, int length) throws IOException {
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
