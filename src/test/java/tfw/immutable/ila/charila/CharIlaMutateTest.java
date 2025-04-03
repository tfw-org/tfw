package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaMutateTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();
        final char value = (char) random.nextInt();

        assertThatThrownBy(() -> CharIlaMutate.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaMutate.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaMutate.create(ila, ilaLength, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=10) >= ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (char) random.nextInt();
            }
            final char value = (char) random.nextInt();
            target[index] = value;
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaMutate.create(origIla, index, value);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
