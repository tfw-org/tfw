package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaMutateUtil;

public final class BooleanIlaMutate {
    private BooleanIlaMutate() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long index, boolean value) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new BooleanIlaImpl(ila, index, value);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long index;
        private final boolean value;

        private BooleanIlaImpl(BooleanIla ila, long index, boolean value) {
            this.ila = ila;
            this.index = index;
            this.value = value;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length();
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
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
