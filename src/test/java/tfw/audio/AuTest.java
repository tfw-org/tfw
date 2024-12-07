package tfw.audio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.audio.au.Au;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaUtil;

final class AuTest {
    @Test
    void auTest() throws Exception {
        final byte[] auBytes1 = new byte[] {
            46, 115, 110, 100, 0, 0, 0, 32, 0, 0, 0, 32, 0, 0, 0, 2, 0, 0, 31, 64, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
            -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15
        };
        final ByteIla auByteIla = ByteIlaFromArray.create(auBytes1);
        final Au au1 = new Au(auByteIla);
        final Au au2 = new Au(au1.encoding, au1.sampleRate, au1.numberOfChannels, au1.annotation, au1.audioData);
        final byte[] auBytes2 = ByteIlaUtil.toArray(au2.auByteData);

        assertThat(auBytes1).isEqualTo(auBytes2);
    }
}
