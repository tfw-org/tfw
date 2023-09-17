package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaFromStridedBooleanIla {
    private BooleanIlaFromStridedBooleanIla() {}

    public static BooleanIla create(final StridedBooleanIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new BooleanIlaImpl(stridedIla);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final StridedBooleanIla stridedIla;

        private BooleanIlaImpl(final StridedBooleanIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final boolean[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
