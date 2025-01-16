package tfw.immutable.ilm.doubleilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class DoubleIlmFromArrayTest {
    @Test
    void doubleIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        double[] array = new double[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextDouble();
        }

        DoubleIlm doubleIlm = DoubleIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(DoubleIlmUtil.toArray(doubleIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
