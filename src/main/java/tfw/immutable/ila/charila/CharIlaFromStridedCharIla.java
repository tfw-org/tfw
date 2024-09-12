package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaFromStridedCharIla {
    private CharIlaFromStridedCharIla() {}

    public static CharIla create(final StridedCharIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new CharIlaImpl(stridedIla);
    }

    private static class CharIlaImpl extends AbstractCharIla {
        private final StridedCharIla stridedIla;

        private CharIlaImpl(final StridedCharIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final char[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
