package tfw.immutable.ila.charila;

import java.io.IOException;
import tfw.check.Argument;

public final class CharIlaInterleave {
    private CharIlaInterleave() {
        // non-instantiable class
    }

    public static CharIla create(CharIla[] ilas, final char[] buffer) throws IOException {
        Argument.assertNotNull(ilas, "ilas");
        Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
        Argument.assertNotNull(ilas[0], "ilas[0]");
        Argument.assertNotNull(buffer, "buffer");

        final long firstLength = ilas[0].length();
        for (int ii = 1; ii < ilas.length; ++ii) {
            Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
            Argument.assertEquals(ilas[ii].length(), firstLength, "ilas[0].length()", "ilas[" + ii + "].length()");
        }

        return new MyCharIla(ilas, buffer);
    }

    private static class MyCharIla extends AbstractCharIla {
        private final StridedCharIla[] stridedCharIlas;
        private final int ilasLength;

        MyCharIla(CharIla[] ilas, final char[] buffer) {
            stridedCharIlas = new StridedCharIla[ilas.length];
            ilasLength = ilas.length;

            for (int i = 0; i < ilas.length; i++) {
                stridedCharIlas[i] = new StridedCharIla(ilas[i], buffer.clone());
            }
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedCharIlas[0].length() * stridedCharIlas.length;
        }

        @Override
        protected void toArrayImpl(char[] array, int offset, long start, int length) throws IOException {
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
                    stridedCharIlas[currentIla].toArray(array, offset, ilaStride, ilaStart, ilaLength);
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
