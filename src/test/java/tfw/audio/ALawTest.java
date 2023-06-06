package tfw.audio;

import java.util.Arrays;
import junit.framework.TestCase;
import tfw.audio.byteila.ALawByteIlaFromLinearShortIla;
import tfw.audio.shortila.LinearShortIlaFromALawByteIla;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

public class ALawTest extends TestCase {
    public void testALaw() throws Exception {
        short[] linearArray = new short[65536];

        for (int i = 0; i < linearArray.length; i++) {
            linearArray[i] = (short) (i + (int) Short.MIN_VALUE);
        }

        ShortIla firstLinearIla = ShortIlaFromArray.create(linearArray);

        ByteIla firstALawIla = ALawByteIlaFromLinearShortIla.create(firstLinearIla);

        ShortIla secondLinearIla = LinearShortIlaFromALawByteIla.create(firstALawIla);

        ByteIla secondALawIla = ALawByteIlaFromLinearShortIla.create(secondLinearIla);

        byte[] firstALawArray = firstALawIla.toArray();
        byte[] secondALawArray = secondALawIla.toArray();

        assertTrue(Arrays.equals(firstALawArray, secondALawArray));
    }
}
