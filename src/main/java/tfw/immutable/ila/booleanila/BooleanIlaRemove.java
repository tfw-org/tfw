package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaRemoveUtil;

public final class BooleanIlaRemove {
    private BooleanIlaRemove() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla ila, long index) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new BooleanIlaImpl(ila, index);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final BooleanIla ila;
        private final long index;

        private BooleanIlaImpl(BooleanIla ila, long index) {
            this.ila = ila;
            this.index = index;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() - 1;
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
            IlaRemoveUtil.get(
                    index,
                    start,
                    length,
                    offset,
                    (destinationOffset, sourceStart, amount) -> ila.get(array, destinationOffset, sourceStart, amount));
        }

        @Override
        protected void closeImpl() throws IOException {
            ila.close();
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
