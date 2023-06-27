package tfw.audio.byteila;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class MuLawByteIlaFromLinearShortIla {
    private static final int BIAS = 0x84;
    private static final int CLIP = 8159;

    private MuLawByteIlaFromLinearShortIla() {}

    public static ByteIla create(ShortIla shortIla) {
        Argument.assertNotNull(shortIla, "shortIla");

        return new MyByteIla(shortIla);
    }

    private static class MyByteIla extends AbstractByteIla {
        private ShortIla shortIla;

        MyByteIla(ShortIla shortIla) {
            super(shortIla.length());

            this.shortIla = shortIla;
        }

        protected void toArrayImpl(byte[] array, int offset, int stride, long start, int length)
                throws DataInvalidException {
            ShortIlaIterator si = new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length));

            for (int i = offset; si.hasNext(); i += stride) {
                /*
                 * The following algorithm is from the file g711.c from
                 * Sun Microsystems which has no use restrictions.  It is
                 * available from the following location:
                 * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
                 */
                int pcm_val = si.next();
                int mask;

                /* Get the sign and the magnitude of the value. */
                pcm_val = pcm_val >> 2;
                if (pcm_val < 0) {
                    pcm_val = -pcm_val;
                    mask = 0x7F;
                } else {
                    mask = 0xFF;
                }

                if (pcm_val > CLIP) {
                    pcm_val = CLIP;
                }
                pcm_val += (BIAS >> 2);

                /* Convert the scaled magnitude to segment number. */
                int seg = 8;

                if (pcm_val <= 0x3F) seg = 0;
                else if (pcm_val <= 0x7F) seg = 1;
                else if (pcm_val <= 0xFF) seg = 2;
                else if (pcm_val <= 0x1FF) seg = 3;
                else if (pcm_val <= 0x3FF) seg = 4;
                else if (pcm_val <= 0x7FF) seg = 5;
                else if (pcm_val <= 0xFFF) seg = 6;
                else if (pcm_val <= 0x1FFF) seg = 7;

                /*
                 * Combine the sign, segment, quantization bits;
                 * and complement the code word.
                 */
                if (seg >= 8) /* out of range, return maximum value. */ {
                    array[i] = (byte) (0x7F ^ mask);
                } else {
                    int uval = (seg << 4) | ((pcm_val >> (seg + 1)) & 0xF);
                    array[i] = (byte) (uval ^ mask);
                }
            }
        }
    }
}
