package tfw.immutable.ilm.objectilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class ObjectIlmFromArrayTest extends TestCase {
    public void testBooleanIlmFromArray() throws Exception {
        Object[] array = new Object[] {1, 2, 3, 4, 5, 6};
        ObjectIlm ObjectIlm = ObjectIlmFromArray.create(array, 3);

        assertTrue(Arrays.equals(array, ObjectIlm.toArray()));
    }
}
