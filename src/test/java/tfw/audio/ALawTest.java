package tfw.audio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.audio.byteila.ALawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromALawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

final class ALawTest {
    @Test
    void alawTest() throws Exception {
        final short[] linearArray = new short[65536];

        for (int i = 0; i < linearArray.length; i++) {
            linearArray[i] = (short) (i + (int) Short.MIN_VALUE);
        }

        final ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);
        final ByteIla firstALawIla = ALawByteIlaFromLinearShortIla.create(firstLinearIla, 1024);
        final ShortIla secondLinearIla = LinearShortIlaFromALawByteIla.create(firstALawIla, 1024);
        final ByteIla secondALawIla = ALawByteIlaFromLinearShortIla.create(secondLinearIla, 1024);
        final byte[] firstALawArray = ByteIlaUtil.toArray(firstALawIla);
        final byte[] secondALawArray = ByteIlaUtil.toArray(secondALawIla);

        assertThat(firstALawArray).isEqualTo(secondALawArray);
    }
}
