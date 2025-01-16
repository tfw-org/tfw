package tfw.immutable.ilm.booleanilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class BooleanIlmFromArrayTest {
    @Test
    void booleanIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        boolean[] array = new boolean[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextBoolean();
        }

        BooleanIlm booleanIlm = BooleanIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(BooleanIlmUtil.toArray(booleanIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
