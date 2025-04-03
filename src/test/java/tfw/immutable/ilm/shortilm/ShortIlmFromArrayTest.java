package tfw.immutable.ilm.shortilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class ShortIlmFromArrayTest {
    @Test
    void shortIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        short[] array = new short[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (short) random.nextInt();
        }

        ShortIlm shortIlm = ShortIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(ShortIlmUtil.toArray(shortIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
