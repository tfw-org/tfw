package tfw.audio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import tfw.audio.byteila.ALawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromALawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaUtil;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

class ALawTest {
    @Test
    void testALaw() throws Exception {
        short[] linearArray = new short[65536];

        for (int i = 0; i < linearArray.length; i++) {
            linearArray[i] = (short) (i + (int) Short.MIN_VALUE);
        }

        ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);

        ByteIla firstALawIla = ALawByteIlaFromLinearShortIla.create(firstLinearIla);

        ShortIla secondLinearIla = LinearShortIlaFromALawByteIla.create(firstALawIla);

        ByteIla secondALawIla = ALawByteIlaFromLinearShortIla.create(secondLinearIla);

        byte[] firstALawArray = ByteIlaUtil.toArray(firstALawIla);
        byte[] secondALawArray = ByteIlaUtil.toArray(secondALawIla);

        assertTrue(Arrays.equals(firstALawArray, secondALawArray));
    }
}
