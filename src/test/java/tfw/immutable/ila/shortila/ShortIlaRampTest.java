package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

final class ShortIlaRampTest {
    @Test
    void argumentsTest() {
        final Random random = new Random(0);
        final short start = (short) random.nextInt();
        final short increment = (short) random.nextInt();

        assertThatThrownBy(() -> ShortIlaRamp.create(start, increment, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("length (=-1) < 0 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final short startValue = (short) random.nextInt();
        final short increment = (short) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        short value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaRamp.create(startValue, increment, length);
        final short epsilon = (short) 0.000001;
        ShortIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
