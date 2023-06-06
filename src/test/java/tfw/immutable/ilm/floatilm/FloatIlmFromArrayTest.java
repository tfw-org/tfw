package tfw.immutable.ilm.floatilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class FloatIlmFromArrayTest extends TestCase {
    public void testBooleanIlmFromArray() throws Exception {
        float[] array = new float[] {1, 2, 3, 4, 5, 6};
        FloatIlm floatIlm = FloatIlmFromArray.create(array, 3);

        assertTrue(Arrays.equals(array, floatIlm.toArray()));
    }
}
