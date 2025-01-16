package tfw.immutable.ilm.longilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class LongIlmFromArrayTest {
    @Test
    void longIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        long[] array = new long[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong();
        }

        LongIlm longIlm = LongIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(LongIlmUtil.toArray(longIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
