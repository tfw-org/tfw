package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class DoubleIlmFromArrayTest extends TestCase {
    public void testBooleanIlmFromArray() throws Exception {
        double[] array = new double[] {1, 2, 3, 4, 5, 6};
        DoubleIlm doubleIlm = DoubleIlmFromArray.create(array, 3);

        assertTrue(Arrays.equals(array, doubleIlm.toArray()));
    }
}
