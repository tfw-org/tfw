package tfw.audio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.audio.byteila.MuLawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromMuLawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class MuLawTest {
    @Test
    void muLawTest() throws Exception {
        final short[] linearArray = new short[65536];

        for (int i = 0; i < linearArray.length; i++) {
            linearArray[i] = (short) (i + (int) Short.MIN_VALUE);
        }

        final ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);
        final ByteIla firstMuLawIla = MuLawByteIlaFromLinearShortIla.create(firstLinearIla, 1024);
        final ShortIla secondLinearIla = LinearShortIlaFromMuLawByteIla.create(firstMuLawIla, 1024);
        final ByteIla secondMuLawIla = MuLawByteIlaFromLinearShortIla.create(secondLinearIla, 1024);
        final byte[] firstMuLawArray = ByteIlaUtil.toArray(firstMuLawIla);
        final byte[] secondMuLawArray = ByteIlaUtil.toArray(secondMuLawIla);

        assertThat(firstMuLawArray).isEqualTo(secondMuLawArray);
    }
}
