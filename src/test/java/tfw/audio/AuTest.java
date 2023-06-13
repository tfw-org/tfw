package tfw.audio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.audio.au.Au;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

class AuTest {
    @Test
    void testAu() throws Exception {
        byte[] auBytes1 = new byte[] {
            46, 115, 110, 100, 0, 0, 0, 32, 0, 0, 0, 32, 0, 0, 0, 2, 0, 0, 31, 64, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,
            -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15
        };

        ByteIla auByteIla = ByteIlaFromArray.create(auBytes1);

        Au au1 = new Au(auByteIla);

        Au au2 = new Au(au1.encoding, au1.sampleRate, au1.numberOfChannels, au1.annotation, au1.audioData);

        byte[] auBytes2 = au2.auByteData.toArray();

        assertTrue(Arrays.equals(auBytes1, auBytes2), "auBytes1 != auBytes2");
    }
}
