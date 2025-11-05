package tfw.audio.byteila;

import java.io.IOException;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.shortila.ShortIla;

abstract class ALawMuLawAbstractByteIla extends AbstractByteIla {
    private static final int[] alawRanges = {0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF, 0x3FFF, 0x7FFF};
    private static final int[] mulawRanges = {0x3F, 0x7F, 0xFF, 0x1FF, 0x3FF, 0x7FF, 0xFFF, 0x1FFF};

    protected final ShortIla shortIla;
    protected final int bufferSize;

    protected ALawMuLawAbstractByteIla(final ShortIla shortIla, final int bufferSize) {
        this.shortIla = shortIla;
        this.bufferSize = bufferSize;
    }

    @Override
    protected final long lengthImpl() throws IOException {
        return shortIla.length();
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
