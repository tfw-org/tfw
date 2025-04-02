package tfw.immutable.iba.booleaniba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class AbstractBooleanIbaTest {
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.valueOf(0);

    @Test
    void closeTest() throws IOException {
        try (TestAbstractBooleanIba tabi = new TestAbstractBooleanIba()) {
            final boolean[] array = new boolean[10];

            assertThat(tabi.getCloseImplCalls()).isZero();
            assertThat(tabi.length()).isEqualTo(BigInteger.valueOf(5));
            tabi.get(array, 0, BIG_INTEGER_ZERO, 5);

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::length)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::length)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 5))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
        }
    }

    @Test
    void getTest() throws IOException {
        try (TestAbstractBooleanIba tabi = new TestAbstractBooleanIba()) {
            final boolean[] expectedArray = new boolean[10];
            final boolean[] actualArray = expectedArray.clone();

            tabi.get(actualArray, 0, BIG_INTEGER_ZERO, 0);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    private static class TestAbstractBooleanIba extends AbstractBooleanIba {
        private int closeImplCalls = 0;

        public int getCloseImplCalls() {
            return closeImplCalls;
        }

        @Override
        protected void closeImpl() throws IOException {
            closeImplCalls++;
        }

        @Override
        protected BigInteger lengthImpl() throws IOException {
            return BigInteger.valueOf(5);
        }

        @Override
        protected void getImpl(boolean[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
            // Nothing to do
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
