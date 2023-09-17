package tfw.immutable.ila.intila;

import java.io.IOException;
import tfw.check.Argument;

public final class IntIlaFromStridedIntIla {
    private IntIlaFromStridedIntIla() {}

    public static IntIla create(final StridedIntIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new IntIlaImpl(stridedIla);
    }

    private static class IntIlaImpl extends AbstractIntIla {
        private final StridedIntIla stridedIla;

        private IntIlaImpl(final StridedIntIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final int[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
