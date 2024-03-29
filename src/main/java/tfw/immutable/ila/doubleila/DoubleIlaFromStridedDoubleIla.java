package tfw.immutable.ila.doubleila;

import java.io.IOException;
import tfw.check.Argument;

public final class DoubleIlaFromStridedDoubleIla {
    private DoubleIlaFromStridedDoubleIla() {}

    public static DoubleIla create(final StridedDoubleIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new DoubleIlaImpl(stridedIla);
    }

    private static class DoubleIlaImpl extends AbstractDoubleIla {
        private final StridedDoubleIla stridedIla;

        private DoubleIlaImpl(final StridedDoubleIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final double[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
