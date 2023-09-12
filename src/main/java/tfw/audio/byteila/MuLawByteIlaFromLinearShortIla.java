package tfw.audio.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

public final class MuLawByteIlaFromLinearShortIla {
    private static final int BIAS = 0x84;
    private static final int CLIP = 8159;

    private MuLawByteIlaFromLinearShortIla() {}

    public static ByteIla create(final ShortIla shortIla, final int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new MyByteIla(shortIla, bufferSize);
    }

    private static class MyByteIla extends AbstractByteIla {
        private final ShortIla shortIla;
        private final int bufferSize;

        MyByteIla(final ShortIla shortIla, final int bufferSize) {
            this.shortIla = shortIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return shortIla.length();
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
                pcm_val += BIAS >> 2;

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
