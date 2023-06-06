package tfw.immutable.ilm.stringilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class StringIlmFromArrayTest extends TestCase {
    public void testBooleanIlmFromArray() throws Exception {
        String[] array = new String[] {"1", "2", "3", "4", "5", "6"};
        StringIlm StringIlm = StringIlmFromArray.create(array, 3);

        assertTrue(Arrays.equals(array, StringIlm.toArray()));
    }
}
