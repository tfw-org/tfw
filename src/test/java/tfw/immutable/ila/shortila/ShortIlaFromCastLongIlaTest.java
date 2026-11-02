package tfw.immutable.ila.shortila;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.TestCloseLongIla;

final class ShortIlaFromCastLongIlaTest {
    @Test
    void argumentsTest() {
        final LongIla ila = LongIlaFromArray.create(new long[10]);

        assertThatThrownBy(() -> ShortIlaFromCastLongIla.create(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIla == null not allowed!");
        assertThatThrownBy(() -> ShortIlaFromCastLongIla.create(ila, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("bufferSize (=0) < 1 not allowed!");
    }

    @Test
    void allTest() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final long[] array = new long[length];
        final short[] target = new short[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = random.nextLong();
            target[ii] = (short) array[ii];
        }
        LongIla ila = LongIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaFromCastLongIla.create(ila, 100);

        ShortIlaCheck.check(targetIla, actualIla);
    }

    @Test
    void closeTest() throws IOException {
        final TestCloseLongIla testIla = new TestCloseLongIla();

        try (ShortIla ila = ShortIlaFromCastLongIla.create(testIla, 100)) {
            assertThat(ila).isNotNull();
        }

        assertThat(testIla.getNumberOfCloses()).isEqualTo(1);
    }
}
// AUTO GENERATED FROM TEMPLATE
