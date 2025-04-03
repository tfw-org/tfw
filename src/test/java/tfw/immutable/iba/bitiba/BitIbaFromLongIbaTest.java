package tfw.immutable.iba.bitiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import tfw.immutable.iba.longiba.LongIba;
import tfw.immutable.iba.longiba.LongIbaFromArray;

class BitIbaFromLongIbaTest {
    @Test
    void parameterTest() {
        final LongIba longIba = LongIbaFromArray.create(new long[1]);
        final BigInteger biNegativeOne = BigInteger.valueOf(-1);

        assertThatThrownBy(() -> BitIbaFromLongIba.create(null, 0, BigInteger.ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIba == null not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, -1, BigInteger.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstLongOffsetInBits (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 64, BigInteger.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstLongOffsetInBits (=64) > 63 not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 0, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits == null not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 0, biNegativeOne))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=-1) < 0 not allowed!");
    }

    @Test
    void goodTest() throws Exception {
        final long[] expectedArray = new long[1];
        final long[] actualArray = expectedArray.clone();
        final LongIba longIba = LongIbaFromArray.create(new long[1]);

        try (BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, BigInteger.TEN)) {
            assertThat(bitIba.lengthInBits()).isEqualTo(BigInteger.TEN);

            bitIba.get(actualArray, 0, BigInteger.ZERO, 10);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }
}
