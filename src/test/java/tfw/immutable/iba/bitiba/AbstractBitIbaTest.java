package tfw.immutable.iba.bitiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class AbstractBitIbaTest {
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.ZERO;
    private static final BigInteger BIG_INTEGER_NEGATIVE_ONE = BigInteger.valueOf(-1);
    private static final BigInteger BIG_INTEGER_ONE = BigInteger.ONE;
    private static final BigInteger BIG_INTEGER_FIVE = BigInteger.valueOf(5);

    @Test
    void closeTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] array = new long[1];

            assertThat(tabi.getCloseImplCalls()).isZero();
            assertThat(tabi.lengthInBits()).isEqualTo(BIG_INTEGER_FIVE);
            tabi.get(array, 0, BIG_INTEGER_ZERO, 5);

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::lengthInBits)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");

            // The closed check must take precedence over null-array validation.
            assertThatThrownBy(() -> tabi.get(null, 0, BIG_INTEGER_ZERO, 0))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::lengthInBits)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
        }
    }

    @Test
    void getNullArrayTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            assertThatThrownBy(() -> tabi.get(null, 0, BIG_INTEGER_ZERO, 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("array (=null) == null not allowed!");

            assertThat(tabi.getImplCalls()).isZero();
        }
    }

    @Test
    void getInvalidParametersTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] array = new long[1];

            assertThatThrownBy(() -> tabi.get(array, -1, BIG_INTEGER_ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_NEGATIVE_ONE, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_FIVE, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 64, BIG_INTEGER_ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);

            assertThat(tabi.getImplCalls()).isZero();
        }
    }

    @Test
    void getZeroLengthTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] array = new long[1];

            tabi.get(array, 0, BIG_INTEGER_ZERO, 0);

            assertThat(tabi.getImplCalls()).isZero();
        }
    }

    @Test
    void getNonzeroLengthTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] array = new long[1];
            final long arrayOffsetInBits = 7;
            final BigInteger ibaStartInBits = BIG_INTEGER_ONE;
            final long lengthInBits = 3;

            tabi.get(array, arrayOffsetInBits, ibaStartInBits, lengthInBits);

            assertThat(tabi.getImplCalls()).isEqualTo(1);
            assertThat(tabi.getLastArray()).isSameAs(array);
            assertThat(tabi.getLastArrayOffsetInBits()).isEqualTo(arrayOffsetInBits);
            assertThat(tabi.getLastIbaStartInBits()).isEqualTo(ibaStartInBits);
            assertThat(tabi.getLastLengthInBits()).isEqualTo(lengthInBits);
        }
    }

    private static class TestAbstractBitIba extends AbstractBitIba {
        private int closeImplCalls;
        private int getImplCalls;
        private long[] lastArray;
        private long lastArrayOffsetInBits;
        private BigInteger lastIbaStartInBits;
        private long lastLengthInBits;

        public int getCloseImplCalls() {
            return closeImplCalls;
        }

        public int getImplCalls() {
            return getImplCalls;
        }

        public long[] getLastArray() {
            return lastArray;
        }

        public long getLastArrayOffsetInBits() {
            return lastArrayOffsetInBits;
        }

        public BigInteger getLastIbaStartInBits() {
            return lastIbaStartInBits;
        }

        public long getLastLengthInBits() {
            return lastLengthInBits;
        }

        @Override
        protected void closeImpl() throws IOException {
            closeImplCalls++;
        }

        @Override
        protected BigInteger lengthInBitsImpl() throws IOException {
            return BIG_INTEGER_FIVE;
        }

        @Override
        protected void getImpl(long[] array, long arrayOffsetInBits, BigInteger ibaStartInBits, long lengthInBits)
                throws IOException {
            getImplCalls++;
            lastArray = array;
            lastArrayOffsetInBits = arrayOffsetInBits;
            lastIbaStartInBits = ibaStartInBits;
            lastLengthInBits = lengthInBits;
        }
    }
}
