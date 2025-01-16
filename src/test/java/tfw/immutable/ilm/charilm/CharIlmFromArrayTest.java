package tfw.immutable.ilm.charilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class CharIlmFromArrayTest {
    @Test
    void charIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        char[] array = new char[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (char) random.nextInt();
        }

        CharIlm charIlm = CharIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(CharIlmUtil.toArray(charIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
