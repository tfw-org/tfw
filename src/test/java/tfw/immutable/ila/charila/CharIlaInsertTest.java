package tfw.immutable.ila.charila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class CharIlaInsertTest {
    @Test
    void argumentsTest() throws Exception {
        final Random random = new Random(0);
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();
        final char value = (char) random.nextInt();

        assertThatThrownBy(() -> CharIlaInsert.create(null, 0, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ila == null not allowed!");
        assertThatThrownBy(() -> CharIlaInsert.create(ila, -1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> CharIlaInsert.create(ila, ilaLength + 1, value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("index (=11) > ila.length() (=10) not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final char[] target = new char[length + 1];
        for (int index = 0; index < length; ++index) {
            final char value = (char) random.nextInt();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = (char) random.nextInt();
            }
            CharIla origIla = CharIlaFromArray.create(array);
            CharIla targetIla = CharIlaFromArray.create(target);
            CharIla actualIla = CharIlaInsert.create(origIla, index, value);

            CharIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
