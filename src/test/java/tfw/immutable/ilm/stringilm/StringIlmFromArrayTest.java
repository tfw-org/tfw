package tfw.immutable.ilm.stringilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class StringIlmFromArrayTest {
    @Test
    void testStringIlmFromArray() throws Exception {
        String[] array = new String[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = new String();
        }

        StringIlm StringIlm = StringIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, StringIlm.toArray()));
    }
}
// AUTO GENERATED FROM TEMPLATE
