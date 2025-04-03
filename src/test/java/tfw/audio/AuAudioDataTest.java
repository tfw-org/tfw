package tfw.audio;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.audio.au.Au;
import tfw.audio.au.AuAudioDataFromNormalizedDoubleIla;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.doubleila.DoubleIla;

final class AuAudioDataTest {
    @Test
    void muLawAuAudioDataTest() throws Exception {
        final byte[] origByteArray = new byte[256];
        final byte[] finalByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
            finalByteArray[i] = (byte) i;
        }
        finalByteArray[127] = (byte) 255; // There are two zeros.

        final ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
        final DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_U_LAW_8_BIT, 1024);
        final ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.ISDN_U_LAW_8_BIT, 1024);
        final byte[] b = ByteIlaUtil.toArray(byteIla);

        assertThat(finalByteArray).isEqualTo(b);
    }

    @Test
    void eightBitLinearAuAudioDataTest() throws Exception {
        final byte[] origByteArray = new byte[256];
        final byte[] finalByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
            finalByteArray[i] = (byte) i;
        }
        finalByteArray[128] = (byte) 129; // -Byte.MAX_VALUE-1 = -Byte.MAX_VALUE

        final ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
        final DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.LINEAR_8_BIT, 1024);
        final ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.LINEAR_8_BIT, 1024);
        final byte[] b = ByteIlaUtil.toArray(byteIla);

        assertThat(finalByteArray).isEqualTo(b);
    }

    @Test
    void aLawAuAudioDataTest() throws Exception {
        final byte[] origByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
        }

        final ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);
        final DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_A_LAW_8_BIT, 1024);
        final ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.ISDN_A_LAW_8_BIT, 1024);
        final byte[] b = ByteIlaUtil.toArray(byteIla);

        assertThat(origByteArray).isEqualTo(b);
    }
}
