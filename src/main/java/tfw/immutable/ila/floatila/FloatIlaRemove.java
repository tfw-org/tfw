package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.IlaRemoveUtil;

public final class FloatIlaRemove {
    private FloatIlaRemove() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, long index) throws IOException {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotLessThan(index, 0, "index");
        Argument.assertLessThan(index, ila.length(), "index", "ila.length()");

        return new FloatIlaImpl(ila, index);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final FloatIla ila;
        private final long index;

        private FloatIlaImpl(FloatIla ila, long index) {
            this.ila = ila;
            this.index = index;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return ila.length() - 1;
        }

        @Override
        protected void getImpl(float[] array, int offset, long start, int length) throws IOException {
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
