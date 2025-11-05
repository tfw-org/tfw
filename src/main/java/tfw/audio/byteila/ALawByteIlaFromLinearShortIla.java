package tfw.audio.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class ALawByteIlaFromLinearShortIla {
    private static final int QUANT_MASK = 0xf;
    private static final int SEG_SHIFT = 4;

    private ALawByteIlaFromLinearShortIla() {}

    public static ByteIla create(final ShortIla shortIla, final int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");

        return new ByteIlaImpl(shortIla, bufferSize);
    }

    private static class ByteIlaImpl extends ALawMuLawAbstractByteIla {
        private ByteIlaImpl(final ShortIla shortIla, final int bufferSize) {
            super(shortIla, bufferSize);
        }

        @Override
        protected void getImpl(byte[] array, int offset, long start, int length) throws IOException {
            ShortIlaIterator si =
                    new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

            for (int i = offset; si.hasNext(); i++) {
                /*
                 * The following algorithm is from the file g711.c from
                 * Sun Microsystems which has no use restrictions.  It is
                 * available from the following location:
                 * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
                 */
                short pcmValue = si.next();
                int mask;

                /* Get the sign and the magnitude of the value. */
                if (pcmValue >= 0) {
                    mask = 0xD5; /* sign (7th) bit = 1 */
                } else {
                    mask = 0x55; /* sign bit = 0 */
                    pcmValue = (short) (-pcmValue - 8);
                }

                /* Convert the scaled magnitude to segment number. */
                int seg = alawSegmentNumberFromScaledMagnitude(pcmValue);

                /* Combine the sign, segment, and quantization bits. */

                if (seg >= 8) /* out of range, return maximum value. */ array[i] = (byte) (0x7F ^ mask);
                else {
                    int aval = seg << SEG_SHIFT;
                    if (seg < 2) aval |= (pcmValue >> 4) & QUANT_MASK;
                    else aval |= (pcmValue >> (seg + 3)) & QUANT_MASK;
                    array[i] = (byte) (aval ^ mask);
                }
            }
        }
    }
}
