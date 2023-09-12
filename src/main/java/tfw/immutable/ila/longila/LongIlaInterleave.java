package tfw.immutable.ila.longila;

import java.io.IOException;
import tfw.check.Argument;

public final class LongIlaInterleave {
    private LongIlaInterleave() {
        // non-instantiable class
    }

    public static LongIla create(LongIla[] ilas, final long[] buffer) throws IOException {
        Argument.assertNotNull(ilas, "ilas");
        Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
        Argument.assertNotNull(ilas[0], "ilas[0]");
        Argument.assertNotNull(buffer, "buffer");

        final long firstLength = ilas[0].length();
        for (int ii = 1; ii < ilas.length; ++ii) {
            Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
            Argument.assertEquals(ilas[ii].length(), firstLength, "ilas[0].length()", "ilas[" + ii + "].length()");
        }

        return new MyLongIla(ilas, buffer);
    }

    private static class MyLongIla extends AbstractLongIla {
        private final StridedLongIla[] stridedLongIlas;
        private final int ilasLength;

        MyLongIla(LongIla[] ilas, final long[] buffer) {
            stridedLongIlas = new StridedLongIla[ilas.length];
            ilasLength = ilas.length;

            for (int i = 0; i < ilas.length; i++) {
                stridedLongIlas[i] = new StridedLongIla(ilas[i], buffer.clone());
            }
        }

        @Override
        protected long lengthImpl() throws IOException {
            return stridedLongIlas[0].length() * stridedLongIlas.length;
        }

        @Override
        protected void getImpl(long[] array, int offset, long start, int length) throws IOException {
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
                    stridedLongIlas[currentIla].get(array, offset, ilaStride, ilaStart, ilaLength);
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
