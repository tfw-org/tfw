package tfw.audio;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.audio.byteila.MuLawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromMuLawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

public class MuLawTest extends TestCase {
    public void testMuLaw() throws Exception {
        short[] linearArray = new short[65536];

        for (int i = 0; i < linearArray.length; i++) {
            linearArray[i] = (short) (i + (int) Short.MIN_VALUE);
        }

        ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);

        ByteIla firstMuLawIla = MuLawByteIlaFromLinearShortIla.create(firstLinearIla);

        ShortIla secondLinearIla = LinearShortIlaFromMuLawByteIla.create(firstMuLawIla);

        ByteIla secondMuLawIla = MuLawByteIlaFromLinearShortIla.create(secondLinearIla);

        byte[] firstMuLawArray = firstMuLawIla.toArray();
        byte[] secondMuLawArray = secondMuLawIla.toArray();

        assertTrue(Arrays.equals(firstMuLawArray, secondMuLawArray));
    }
}
