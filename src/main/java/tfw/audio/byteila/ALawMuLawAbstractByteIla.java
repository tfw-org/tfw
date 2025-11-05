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
}
