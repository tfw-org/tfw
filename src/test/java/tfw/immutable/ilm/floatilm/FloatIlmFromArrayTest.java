package tfw.immutable.ilm.floatilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class FloatIlmFromArrayTest {
    @Test
    void floatIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        float[] array = new float[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextFloat();
        }

        FloatIlm floatIlm = FloatIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(FloatIlmUtil.toArray(floatIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
