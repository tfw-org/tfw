package tfw.audio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.audio.au.Au;
import tfw.audio.au.AuAudioDataFromNormalizedDoubleIla;
import tfw.audio.au.NormalizedDoubleIlaFromAuAudioData;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.doubleila.DoubleIla;

class AuAudioDataTest {
    @Test
    void testMuLawAuAudioData() throws Exception {
        byte[] origByteArray = new byte[256];
        byte[] finalByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
            finalByteArray[i] = (byte) i;
        }
        finalByteArray[127] = (byte) 255; // There are two zeros.

        ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);

        DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_U_LAW_8_BIT);
        ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.ISDN_U_LAW_8_BIT);

        byte[] b = ByteIlaUtil.toArray(byteIla);

        assertTrue(Arrays.equals(finalByteArray, b));
    }

    public void test8BitLinearAuAudioData() throws Exception {
        byte[] origByteArray = new byte[256];
        byte[] finalByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
            finalByteArray[i] = (byte) i;
        }
        finalByteArray[128] = (byte) 129; // -Byte.MAX_VALUE-1 = -Byte.MAX_VALUE

        ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);

        DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.LINEAR_8_BIT);

        ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.LINEAR_8_BIT);

        byte[] b = ByteIlaUtil.toArray(byteIla);

        assertTrue(Arrays.equals(finalByteArray, b));
    }

    public void testALawAuAudioData() throws Exception {
        byte[] origByteArray = new byte[256];

        for (int i = 0; i < origByteArray.length; i++) {
            origByteArray[i] = (byte) i;
        }

        ByteIla origByteIla = ByteIlaFromArray.create(origByteArray);

        DoubleIla doubleIla =
                NormalizedDoubleIlaFromAuAudioData.create(origByteIla, Au.SUN_MAGIC_NUMBER, Au.ISDN_A_LAW_8_BIT);
        ByteIla byteIla = AuAudioDataFromNormalizedDoubleIla.create(doubleIla, Au.ISDN_A_LAW_8_BIT);

        byte[] b = ByteIlaUtil.toArray(byteIla);

        assertTrue(Arrays.equals(origByteArray, b));
    }
}
