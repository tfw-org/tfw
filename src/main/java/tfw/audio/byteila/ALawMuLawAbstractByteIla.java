package tfw.audio.byteila;

import java.io.IOException;
import tfw.check.Argument;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaIterator;
import tfw.immutable.ila.shortila.ShortIlaSegment;

abstract class ALawMuLawAbstractByteIla extends AbstractByteIla {
    private static final int[] alawRanges = {0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF};
    private static final int[] mulawRanges = {0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF};

    protected final ShortIla shortIla;
    protected final int bufferSize;

    protected abstract void processValue(final int pcmValue, final byte[] array, final int offset);

    protected ALawMuLawAbstractByteIla(final ShortIla shortIla, final int bufferSize) {
        Argument.assertNotNull(shortIla, "shortIla");
        Argument.assertNotLessThan(bufferSize, 1, "bufferSize");

        this.shortIla = shortIla;
        this.bufferSize = bufferSize;
    }

    @Override
    protected final long lengthImpl() throws IOException {
        return shortIla.length();
    }

    @Override
    protected final void getImpl(byte[] array, int offset, long start, int length) throws IOException {
        ShortIlaIterator si =
                new ShortIlaIterator(ShortIlaSegment.create(shortIla, start, length), new short[bufferSize]);

        for (int i = offset; si.hasNext(); i++) {
            processValue(si.next(), array, i);
        }
    }

    public static int alawSegmentNumberFromScaledMagnitude(final int pcmValue) {
        return segmentNumberFromScaledMagnitude(pcmValue, alawRanges);
    }

    public static int mulawSegmentNumberFromScaledMagnitude(final int pcmValue) {
        return segmentNumberFromScaledMagnitude(pcmValue, mulawRanges);
    }

    private static int segmentNumberFromScaledMagnitude(final int pcmValue, final int[] ranges) {
        for (int i = 0; i < ranges.length; i++) {
            if (pcmValue <= ranges[i]) {
                return i;
            }
        }

        return ranges.length;
    }
}
