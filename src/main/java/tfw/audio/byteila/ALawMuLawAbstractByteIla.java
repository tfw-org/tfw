package tfw.audio.byteila;

import java.io.IOException;
import tfw.immutable.ila.byteila.AbstractByteIla;
import tfw.immutable.ila.shortila.ShortIla;

abstract class ALawMuLawAbstractByteIla extends AbstractByteIla {
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
        if (pcmValue <= 0xFF) {
            return 0;
        } else if (pcmValue <= 0x1FF) {
            return 1;
        } else if (pcmValue <= 0x3FF) {
            return 2;
        } else if (pcmValue <= 0x7FF) {
            return 3;
        } else if (pcmValue <= 0xFFF) {
            return 4;
        } else if (pcmValue <= 0x1FFF) {
            return 5;
        } else if (pcmValue <= 0x3FFF) {
            return 6;
        } else if (pcmValue <= 0x7FFF) {
            return 7;
        } else {
            return 8;
        }
    }

    public static int mulawSegmentNumerFromScaledMagnitude(final int pcmValue) {
        if (pcmValue <= 0x3F) {
            return 0;
        } else if (pcmValue <= 0x7F) {
            return 1;
        } else if (pcmValue <= 0xFF) {
            return 2;
        } else if (pcmValue <= 0x1FF) {
            return 3;
        } else if (pcmValue <= 0x3FF) {
            return 4;
        } else if (pcmValue <= 0x7FF) {
            return 5;
        } else if (pcmValue <= 0xFFF) {
            return 6;
        } else if (pcmValue <= 0x1FFF) {
            return 7;
        } else {
            return 8;
        }
    }
}
