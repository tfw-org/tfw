package tfw.audio.shortila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaSegment;
import tfw.immutable.ila.shortila.AbstractShortIla;
import tfw.immutable.ila.shortila.ShortIla;

public final class LinearShortIlaFromALawByteIla {
    private static final int QUANT_MASK = 0xf;
    private static final int SEG_MASK = 0x70;
    private static final int SEG_SHIFT = 4;
    private static final int SIGN_BIT = 0x80;

    private LinearShortIlaFromALawByteIla() {}

    public static ShortIla create(final ByteIla byteIla, final int bufferSize) {
        Argument.assertNotNull(byteIla, "byteIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        return new ShortIlaImpl(byteIla, bufferSize);
    }

    private static class ShortIlaImpl extends AbstractShortIla {
        private final ByteIla byteIla;
        private final int bufferSize;

        private ShortIlaImpl(final ByteIla byteIla, final int bufferSize) {
            this.byteIla = byteIla;
            this.bufferSize = bufferSize;
        }

        @Override
        protected long lengthImpl() throws IOException {
            return byteIla.length();
        }

        @Override
        protected void getImpl(short[] array, int offset, long start, int length) throws IOException {
            ByteIlaIterator bi =
                    new ByteIlaIterator(ByteIlaSegment.create(byteIla, start, length), new byte[bufferSize]);

            for (int i = offset; bi.hasNext(); i++) {
                /*
                 * The following algorithm is from the file g711.c from
                 * Sun Microsystems which has no use restrictions.  It is
                 * available from the following location:
                 * ftp://svr-ftp.eng.cam.ac.uk/pub/comp.speech/coding/G711_G721_G723.tar.gz
                 */
                int aLawValue = bi.next();

                aLawValue ^= 0x55;

                int t = (aLawValue & QUANT_MASK) << 4;
                int seg = (aLawValue & SEG_MASK) >> SEG_SHIFT;

                switch (seg) {
                    case 0:
                        t += 8;
                        break;
                    case 1:
                        t += 0x108;
                        break;
                    default:
                        t += 0x108;
                        t <<= seg - 1;
                }

                short s = (short) t;

                array[i] = (short) (((aLawValue & SIGN_BIT) != 0) ? s : -s);
            }
        }
    }
}
