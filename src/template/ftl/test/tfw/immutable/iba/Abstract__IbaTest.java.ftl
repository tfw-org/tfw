// booleaniba,byteiba,chariba,doubleiba,floatiba,intiba,longiba,objectiba,shortiba
package ${PACKAGE};

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class Abstract${NAME}IbaTest {
    private static final BigInteger BIG_INTEGER_ZERO = BigInteger.ZERO;
    private static final BigInteger BIG_INTEGER_ONE = BigInteger.ONE;
    private static final BigInteger BIG_INTEGER_NEGATIVE_ONE = BigInteger.valueOf(-1);

    @Test
    void closeTest() throws IOException {
        try (TestAbstract${NAME}Iba tabi = new TestAbstract${NAME}Iba()) {
            final ${TYPE}[] array = new ${TYPE}[10];

            assertThat(tabi.getCloseImplCalls()).isZero();
            assertThat(tabi.length()).isEqualTo(BigInteger.valueOf(5));
            tabi.get(array, 0, BIG_INTEGER_ZERO, 5);

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::length)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
            assertThatThrownBy(() -> tabi.get(null, 0, BIG_INTEGER_ZERO, 1))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");

            tabi.close();

            assertThat(tabi.getCloseImplCalls()).isEqualTo(1);
            assertThatThrownBy(tabi::length)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("This object is closed!");
        }
    }

    @Test
    void getTest() throws IOException {
        try (TestAbstract${NAME}Iba tabi = new TestAbstract${NAME}Iba()) {
            final ${TYPE}[] array = new ${TYPE}[10];

            tabi.get(array, 0, BIG_INTEGER_ZERO, 0);

            assertThat(tabi.getImplCalls()).isZero();

            tabi.get(array, 2, BIG_INTEGER_ONE, 2);

            assertThat(tabi.getImplCalls()).isEqualTo(1);
            assertThat(tabi.getLastArray()).isSameAs(array);
            assertThat(tabi.getLastArrayOffset()).isEqualTo(2);
            assertThat(tabi.getLastIbaStart()).isEqualTo(BIG_INTEGER_ONE);
            assertThat(tabi.getLastLength()).isEqualTo(2);
        }
    }

    @Test
    void argumentsTest() throws IOException {
        try (TestAbstract${NAME}Iba tabi = new TestAbstract${NAME}Iba()) {
            final ${TYPE}[] array = new ${TYPE}[10];

            assertThatThrownBy(() -> tabi.get(null, 0, BIG_INTEGER_ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, -1, BIG_INTEGER_ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_NEGATIVE_ONE, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> tabi.get(array, 0, BIG_INTEGER_ZERO, 6))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    private static class TestAbstract${NAME}Iba extends Abstract${NAME}Iba${TEMPLATE} {
        private int closeImplCalls = 0;
        private int getImplCalls = 0;
        private ${TYPE_OR_TEMPLATE}[] lastArray;
        private int lastArrayOffset;
        private BigInteger lastIbaStart;
        private int lastLength;

        public int getCloseImplCalls() {
            return closeImplCalls;
        }

        public int getImplCalls() {
            return getImplCalls;
        }

        public ${TYPE_OR_TEMPLATE}[] getLastArray() {
            return lastArray;
        }

        public int getLastArrayOffset() {
            return lastArrayOffset;
        }

        public BigInteger getLastIbaStart() {
            return lastIbaStart;
        }

        public int getLastLength() {
            return lastLength;
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
        protected void getImpl(${TYPE_OR_TEMPLATE}[] array, int arrayOffset, BigInteger ibaStart, int length) throws IOException {
            getImplCalls++;
            lastArray = array;
            lastArrayOffset = arrayOffset;
            lastIbaStart = ibaStart;
            lastLength = length;
        }
    }
}
