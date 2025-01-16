package tfw.immutable.ilm.objectilm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class ObjectIlmFromArrayTest {
    @Test
    void objectIlmFromArrayTest() throws Exception {
        Object[] array = new Object[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }

        ObjectIlm<Object> objectIlm = ObjectIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(ObjectIlmUtil.toArray(objectIlm, new Object[0]));
    }
}
