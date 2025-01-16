package tfw.immutable.ilm.byteilm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

final class ByteIlmFromArrayTest {
    @Test
    void byteIlmFromArrayTest() throws Exception {
        final Random random = new Random(0);
        byte[] array = new byte[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) random.nextInt();
        }

        ByteIlm byteIlm = ByteIlmFromArray.create(array, array.length / 2);

        assertThat(array).isEqualTo(ByteIlmUtil.toArray(byteIlm));
    }
}
// AUTO GENERATED FROM TEMPLATE
