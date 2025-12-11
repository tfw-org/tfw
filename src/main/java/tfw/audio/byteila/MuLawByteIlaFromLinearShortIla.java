package tfw.audio.byteila;

import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;

public final class MuLawByteIlaFromLinearShortIla {
    private static final int BIAS = 0x84;
    private static final int CLIP = 8159;

    private MuLawByteIlaFromLinearShortIla() {}

    public static ByteIla create(final ShortIla shortIla, final int bufferSize) {
        return new ByteIlaImpl(shortIla, bufferSize);
    }

    private static class ByteIlaImpl extends ALawMuLawAbstractByteIla {
        private ByteIlaImpl(final ShortIla shortIla, final int bufferSize) {
            super(shortIla, bufferSize);
        }

        @Override
        protected void processValue(final int value, final byte[] array, final int offset) {
            /*
             * The following algorithm is from the file g711.c from
             * Sun Microsystems which has no use restrictions.  It is
             * available from the following location:
             * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
             */
            int pcmValue = value;
            int mask;

            /* Get the sign and the magnitude of the value. */
            pcmValue = pcmValue >> 2;
            if (pcmValue < 0) {
                pcmValue = -pcmValue;
                mask = 0x7F;
            } else {
                mask = 0xFF;
            }

            if (pcmValue > CLIP) {
                pcmValue = CLIP;
            }
            pcmValue += BIAS >> 2;

            /* Convert the scaled magnitude to segment number. */
            int seg = mulawSegmentNumberFromScaledMagnitude(pcmValue);

            /*
             * Combine the sign, segment, quantization bits;
             * and complement the code word.
             */
            if (seg >= 8) /* out of range, return maximum value. */ {
                array[offset] = (byte) (0x7F ^ mask);
            } else {
                int uval = (seg << 4) | ((pcmValue >> (seg + 1)) & 0xF);
                array[offset] = (byte) (uval ^ mask);
            }
        }
    }
}
