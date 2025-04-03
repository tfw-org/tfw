package tfw.immutable.ila.booleanila;

import java.io.IOException;
import tfw.check.Argument;

public final class BooleanIlaInterleave {
    private BooleanIlaInterleave() {
        // non-instantiable class
    }

    public static BooleanIla create(BooleanIla[] ilas, final boolean[] buffer) {
        return new BooleanIlaImpl(ilas, buffer);
    }

    private static class BooleanIlaImpl extends AbstractBooleanIla {
        private final StridedBooleanIla[] stridedBooleanIlas;
        private final int ilasLength;

        private BooleanIlaImpl(BooleanIla[] ilas, final boolean[] buffer) {
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

            stridedBooleanIlas = new StridedBooleanIla[ilas.length];
            ilasLength = ilas.length;

            for (int i = 0; i < ilas.length; i++) {
                stridedBooleanIlas[i] = StridedBooleanIlaFromBooleanIla.create(ilas[i], buffer.clone());
            }
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedBooleanIlas[0].length() * stridedBooleanIlas.length;
        }

        @Override
        protected void getImpl(boolean[] array, int offset, long start, int length) throws IOException {
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
                    stridedBooleanIlas[currentIla].get(array, offset, ilaStride, ilaStart, ilaLength);
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
