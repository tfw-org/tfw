package tfw.immutable.ila.shortila;

import java.io.IOException;
import tfw.check.Argument;

public final class ShortIlaFromStridedShortIla {
    private ShortIlaFromStridedShortIla() {}

    public static ShortIla create(final StridedShortIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new ShortIlaImpl(stridedIla);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final StridedShortIla stridedIla;

        private ShortIlaImpl(final StridedShortIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final short[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
