package tfw.immutable.ilm.objectilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ObjectIlmFromArrayTest {
    @Test
    void testObjectIlmFromArray() throws Exception {
        Object[] array = new Object[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }

        ObjectIlm ObjectIlm = ObjectIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, ObjectIlmUtil.toArray(ObjectIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
