// booleaniba,byteiba,chariba,doubleiba,floatiba,intiba,longiba,objectiba,shortiba
package ${PACKAGE};

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class ${NAME}IbaFromArrayTest {
    @Test
    void parameterTest() {
        assertThatThrownBy(() -> ${NAME}IbaFromArray.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("array (=null) == null not allowed!");
    }

    @Test
    void validTest() throws Exception {
        final ${TYPE}[] expectedArray = new ${TYPE}[10];
        final ${TYPE}[] actualArray = expectedArray.clone();

        Arrays.fill(actualArray, ${DEFAULT_VALUE});
        Arrays.fill(expectedArray, ${DEFAULT_VALUE_2});

        try (${NAME}Iba iba = ${NAME}IbaFromArray.create(expectedArray)) {
            assertThat(iba.length()).isEqualTo(BigInteger.TEN);

            iba.get(actualArray, 0, BigInteger.ZERO, actualArray.length);

            assertThat(actualArray).isEqualTo(expectedArray);
        }
    }

    @Test
    void boundaryTest() throws Exception {
        final ${TYPE}[] source = new ${TYPE}[] {${DEFAULT_VALUE_2}, ${DEFAULT_VALUE_2}, ${DEFAULT_VALUE_2}};
        final ${TYPE}[] destination = new ${TYPE}[3];

        try (${NAME}Iba iba = ${NAME}IbaFromArray.create(source)) {
            iba.get(destination, 0, BigInteger.ZERO, 3);
            assertThat(destination).isEqualTo(source);

            // An empty range at the exact end of either array is valid.
            iba.get(destination, destination.length, BigInteger.valueOf(3), 0);
            iba.get(destination, 0, BigInteger.valueOf(3), 0);

            // Empty ranges beyond either end are invalid.
            assertThatThrownBy(() -> iba.get(destination, destination.length + 1, BigInteger.valueOf(3), 0))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.valueOf(4), 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void invalidRangeTest() throws Exception {
        final ${TYPE}[] source = new ${TYPE}[3];
        final ${TYPE}[] destination = new ${TYPE}[3];

        try (${NAME}Iba iba = ${NAME}IbaFromArray.create(source)) {
            assertThatThrownBy(() -> iba.get(destination, -1, BigInteger.ZERO, 1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, -1))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 2, BigInteger.ZERO, 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.valueOf(2), 2))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> iba.get(null, 0, BigInteger.ZERO, 0)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void closedTest() throws Exception {
        final ${TYPE}[] source = new ${TYPE}[1];
        final ${TYPE}[] destination = new ${TYPE}[1];

        final ${NAME}Iba iba = ${NAME}IbaFromArray.create(source);
        iba.close();

        assertThatThrownBy(() -> iba.get(destination, 0, BigInteger.ZERO, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This object is closed!");
    }
}
