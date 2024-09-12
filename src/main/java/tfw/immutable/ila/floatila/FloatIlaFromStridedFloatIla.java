package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaFromStridedFloatIla {
    private FloatIlaFromStridedFloatIla() {}

    public static FloatIla create(final StridedFloatIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new FloatIlaImpl(stridedIla);
    }

    private static class FloatIlaImpl extends AbstractFloatIla {
        private final StridedFloatIla stridedIla;

        private FloatIlaImpl(final StridedFloatIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final float[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
