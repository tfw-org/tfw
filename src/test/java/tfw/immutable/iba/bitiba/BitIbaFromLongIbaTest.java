package tfw.immutable.iba.bitiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import tfw.immutable.iba.longiba.LongIba;
import tfw.immutable.iba.longiba.LongIbaFromArray;

class BitIbaFromLongIbaTest {
    private static final BigInteger BIG_INTEGER_NEGATIVE_ONE = BigInteger.valueOf(-1);
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.ZERO;
    private static final BigInteger BIG_INTEGER_TEN = BigInteger.TEN;
    private static final BigInteger EIGHT = new BigInteger("8");
    private static final BigInteger SIXTY_THREE = new BigInteger("63");
    private static final BigInteger SIXTY_FOUR = new BigInteger("64");
    private static final BigInteger SIXTY_FIVE = new BigInteger("65");
    private static final long SOURCE_WORD = 0xA5C3_69F0_1234_5678L;
    private static final long EXPECTED_WORD = 0xA500_0000_0000_0000L;

    @Test
    void parameterTest() {
        final LongIba longIba = LongIbaFromArray.create(new long[1]);

        assertThatThrownBy(() -> BitIbaFromLongIba.create(null, 0, BIG_INTEGER_ZERO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("longIba (=null) == null not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, -1, BIG_INTEGER_TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstLongOffsetInBits (=-1) < 0 not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 64, BIG_INTEGER_TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("firstLongOffsetInBits (=64) > 63 not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 0, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=null) == null not allowed!");
        assertThatThrownBy(() -> BitIbaFromLongIba.create(longIba, 0, BIG_INTEGER_NEGATIVE_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lengthInBits (=-1) < 0 not allowed!");
    }

    @Test
    void closeTest() throws Exception {
        final LongIba longIba = LongIbaFromArray.create(new long[] {SOURCE_WORD});
        final BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, BIG_INTEGER_TEN);

        bitIba.close();

        assertThatThrownBy(() -> longIba.get(new long[1], 0, BIG_INTEGER_ZERO, 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }

    @Test
    void goodTest() throws Exception {
        final long[] expectedArray = {EXPECTED_WORD};
        final long[] actualArray = new long[1];
        final LongIba longIba = LongIbaFromArray.create(new long[] {SOURCE_WORD});

        try (BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, BIG_INTEGER_TEN)) {
            assertThat(bitIba.lengthInBits()).isEqualTo(BIG_INTEGER_TEN);

            bitIba.get(actualArray, 0, BIG_INTEGER_ZERO, 8);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void getNonzeroStartTest() throws Exception {
        final long source = 0x0123456789ABCDEFL;
        final long[] actualArray = new long[1];
        final LongIba longIba = LongIbaFromArray.create(new long[] {source});

        try (BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, EIGHT.add(BigInteger.ONE))) {
            bitIba.get(actualArray, 0, BigInteger.ONE, 8);

            assertThat(actualArray).containsExactly(0x0200000000000000L);
        }
    }

    @Test
    void getAcrossLongBoundaryTest() throws Exception {
        final long[] source = {0x0123456789ABCDEFL, 0xFEDCBA9876543210L};
        final long[] actualArray = new long[1];
        final LongIba longIba = LongIbaFromArray.create(source);

        try (BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, SIXTY_FIVE)) {
            bitIba.get(actualArray, 0, SIXTY_THREE, 2);

            assertThat(actualArray).containsExactly(0xC000000000000000L);
        }
    }

    @Test
    void getEndingAtLongBoundaryTest() throws Exception {
        final long source = 0x0123456789ABCDEFL;
        final long[] actualArray = new long[1];
        final LongIba longIba = LongIbaFromArray.create(new long[] {source});

        try (BitIba bitIba = BitIbaFromLongIba.create(longIba, 0, SIXTY_FOUR)) {
            bitIba.get(actualArray, 0, BigInteger.ZERO, 64);

            assertThat(actualArray).containsExactly(source);
        }
    }
}
