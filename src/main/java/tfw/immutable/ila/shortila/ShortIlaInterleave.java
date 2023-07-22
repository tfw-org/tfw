package tfw.immutable.ila.shortila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public final class ShortIlaInterleave {
    private ShortIlaInterleave() {
        // non-instantiable class
    }

    public static ShortIla create(ShortIla[] ilas) {
        Argument.assertNotNull(ilas, "ilas");
        Argument.assertNotLessThan(ilas.length, 1, "ilas.length");
        Argument.assertNotNull(ilas[0], "ilas[0]");
        final long firstLength = ilas[0].length();
        for (int ii = 1; ii < ilas.length; ++ii) {
            Argument.assertNotNull(ilas[ii], "ilas[" + ii + "]");
            Argument.assertEquals(ilas[ii].length(), firstLength, "ilas[0].length()", "ilas[" + ii + "].length()");
        }

        return new MyShortIla(ilas);
    }

    private static class MyShortIla extends AbstractShortIla {
        private final ShortIla[] ilas;

        private final int ilasLength;

        MyShortIla(ShortIla[] ilas) {
            super(ilas[0].length() * ilas.length);
            this.ilas = ilas;
            this.ilasLength = ilas.length;
        }

        protected void toArrayImpl(short[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            int currentIla = (int) (start % ilasLength);
            long ilaStart = start / ilasLength;
            final int ilaStride = stride * ilasLength;
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
                    ilas[currentIla].toArray(array, offset, ilaStride, ilaStart, ilaLength);
                }
                offset += stride;
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
