package tfw.immutable.ila.floatila;

import java.io.IOException;
import tfw.check.Argument;

public final class FloatIlaInterleave {
    private FloatIlaInterleave() {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla[] ilas, final float[] buffer) {
        Argument.assertNotNull(ilas, "ilas");
        Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
        Argument.assertNotNull(ilas[0], "ilas[0]");
        Argument.assertNotNull(buffer, "buffer");

        final long firstLength = ilas[0].length();
        for (int ii = 1; ii < ilas.length; ++ii) {
            Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
            Argument.assertEquals(ilas[ii].length(), firstLength, "ilas[0].length()", "ilas[" + ii + "].length()");
        }

        return new MyFloatIla(ilas, buffer);
    }

    private static class MyFloatIla extends AbstractFloatIla {
        private final StridedFloatIla[] stridedFloatIlas;
        private final int ilasLength;

        MyFloatIla(FloatIla[] ilas, final float[] buffer) {
            super(ilas[0].length() * ilas.length);

            stridedFloatIlas = new StridedFloatIla[ilas.length];
            ilasLength = ilas.length;

            for (int i = 0; i < ilas.length; i++) {
                stridedFloatIlas[i] = new StridedFloatIla(ilas[i], buffer.clone());
            }
        }

        protected void toArrayImpl(float[] array, int offset, long start, int length) throws IOException {
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
                    stridedFloatIlas[currentIla].toArray(array, offset, ilaStride, ilaStart, ilaLength);
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
