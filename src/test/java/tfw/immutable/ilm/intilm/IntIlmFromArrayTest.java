package tfw.immutable.ilm.intilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class IntIlmFromArrayTest {
    @Test
    void intIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        int[] array = new int[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }

        IntIlm intIlm = IntIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(IntIlmUtil.toArray(intIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
