package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaFromStridedByteIla {
    private ByteIlaFromStridedByteIla() {}

    public static ByteIla create(final StridedByteIla stridedIla) {
        Argument.assertNotNull(stridedIla, "stridedIla");

        return new ByteIlaImpl(stridedIla);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final StridedByteIla stridedIla;

        private ByteIlaImpl(final StridedByteIla stridedIla) {
            this.stridedIla = stridedIla;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedIla.length();
        }

        @Override
        public void getImpl(final byte[] array, final int offset, final long start, int length) throws IOException {
            stridedIla.get(array, offset, 1, start, length);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
