package tfw.immutable.ila.byteila;

import java.io.IOException;
import tfw.check.Argument;

public final class ByteIlaInterleave {
    private ByteIlaInterleave() {
        // non-instantiable class
    }

    public static ByteIla create(ByteIla[] ilas, final byte[] buffer) {
        return new ByteIlaImpl(ilas, buffer);
    }

    private static class ByteIlaImpl extends AbstractByteIla {
        private final StridedByteIla[] stridedByteIlas;
        private final int ilasLength;

        private ByteIlaImpl(ByteIla[] ilas, final byte[] buffer) {
            Argument.assertNotNull(ilas, "ilas");
            Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
            Argument.assertNotNull(ilas[0], "ilas[0]");
            Argument.assertNotNull(buffer, "buffer");

            try {
                final long firstLength = ilas[0].length();
                for (int ii = 1; ii < ilas.length; ++ii) {
                    Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
                    Argument.assertEquals(
                            ilas[ii].length(), firstLength, "ilas[0].length()", "ilas[" + ii + "].length()");
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not get ila length()!", e);
            }

            stridedByteIlas = new StridedByteIla[ilas.length];
            ilasLength = ilas.length;

            for (int i = 0; i < ilas.length; i++) {
                stridedByteIlas[i] = StridedByteIlaFromByteIla.create(ilas[i], buffer.clone());
            }
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedByteIlas[0].length() * stridedByteIlas.length;
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            int currentIla = (int) (start % ilasLength);
            long ilaStart = start / ilasLength;
            final int ilaStride = ilasLength;
            int ilaLength = (length + ilasLength - 1) / ilasLength;
            int lengthIndex = length % ilasLength;
            if (lengthIndex == 0) {
                // invalidate lengthIndex so we don't decrement ilaLength
                // at index 0
                --lengthIndex;
            }

            for (int ii = 0; ii < ilasLength; ++ii) {
                if (ii == lengthIndex) {
                    --ilaLength;
                }
                if (ilaLength > 0) {
                    stridedByteIlas[currentIla].get(array, offset, ilaStride, ilaStart, ilaLength);
                }
                offset++;
                ++currentIla;
                if (currentIla == ilasLength) {
                    currentIla = 0;
                    ++ilaStart;
                }
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
