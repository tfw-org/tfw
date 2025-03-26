package tfw.immutable.iba.bitiba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class AbstractBitIbaTest {
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.valueOf(0);

    @Test
    void closeTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] array = new long[1];

            assertThat(tabi.getCloseImplCalls()).isZero();
            assertThat(tabi.lengthInBits()).isEqualTo(BigInteger.valueOf(5));
            tabi.get(array, 0, BIG_INTEGER_ZERO, 5);

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::lengthInBits)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This BitIla is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This BitIla is closed!");

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::lengthInBits)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This BitIla is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This BitIla is closed!");
        }
    }

    @Test
    void getTest() throws IOException {
        try (TestAbstractBitIba tabi = new TestAbstractBitIba()) {
            final long[] expectedArray = new long[1];
            final long[] actualArray = expectedArray.clone();

            tabi.get(actualArray, 0, BIG_INTEGER_ZERO, 0);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    private static class TestAbstractBitIba extends AbstractBitIba {
        private int closeImplCalls = 0;

        public int getCloseImplCalls() {
            return closeImplCalls;
        }

        @Override
        protected void closeImpl() throws IOException {
            closeImplCalls++;
        }

        @Override
        protected BigInteger lengthInBitsImpl() throws IOException {
            return BigInteger.valueOf(5);
        }

        @Override
        protected void getImpl(long[] array, long arrayOffset, BigInteger ibaStart, long length) throws IOException {
            // Nothing to do
        }
    }
}
