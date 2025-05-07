package tfw.check;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

final class ArgumentsTest {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final byte ZERO_BYTE = 0;
    private static final byte ONE_BYTE = 1;
    private static final char ZERO_CHAR = 0;
    private static final char ONE_CHAR = 1;
    private static final short ZERO_SHORT = 0;
    private static final short ONE_SHORT = 1;
    private static final int ZERO_INT = 0;
    private static final int ONE_INT = 1;
    private static final long ZERO_LONG = 0L;
    private static final long ONE_LONG = 1L;
    private static final float ZERO_FLOAT = 0.0F;
    private static final float ONE_FLOAT = 1.0F;
    private static final double ZERO_DOUBLE = 0.0;
    private static final double ONE_DOUBLE = 1.0;
    private static final Object ZERO_OBJECT = new Object();
    private static final Object ONE_OBJECT = new Object();
    private static final Object NULL_OBJECT = null;

    @Test
    void booleanSuccessTest() {
        Arguments.checkEquals(false, LEFT, false, RIGHT);
        Arguments.checkEquals(true, LEFT, true, RIGHT);
        Arguments.checkNotEquals(false, LEFT, true, RIGHT);
        Arguments.checkNotEquals(true, LEFT, false, RIGHT);
        Arguments.checkTrue(true, LEFT);
        Arguments.checkFalse(false, LEFT);
    }

    @Test
    void booleanFailureTest() {
        assertThatThrownBy(() -> Arguments.checkEquals(false, LEFT, true, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=false) != right (=true) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(true, LEFT, false, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=true) != right (=false) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(false, LEFT, false, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=false) == right (=false) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(true, LEFT, true, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=true) == right (=true) not allowed!");
        assertThatThrownBy(() -> Arguments.checkTrue(false, LEFT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=false) != true not allowed!");
        assertThatThrownBy(() -> Arguments.checkFalse(true, LEFT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=true) != false not allowed!");
    }

    @Test
    void byteSuccessTest() {
        Arguments.checkLessThan(ZERO_BYTE, LEFT, ONE_BYTE, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT);
        Arguments.checkEquals(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT);
        Arguments.checkNotEquals(ZERO_BYTE, LEFT, ONE_BYTE, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT);
        Arguments.checkGreaterThan(ONE_BYTE, LEFT, ZERO_BYTE, RIGHT);
    }

    @Test
    void byteFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_BYTE, LEFT, ZERO_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_BYTE, LEFT, ONE_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_BYTE, LEFT, ONE_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_BYTE, LEFT, ZERO_BYTE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void charSuccessTest() {
        Arguments.checkLessThan(ZERO_CHAR, LEFT, ONE_CHAR, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT);
        Arguments.checkEquals(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT);
        Arguments.checkNotEquals(ZERO_CHAR, LEFT, ONE_CHAR, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT);
        Arguments.checkGreaterThan(ONE_CHAR, LEFT, ZERO_CHAR, RIGHT);
    }

    @Test
    void charFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_CHAR, LEFT, ZERO_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_CHAR, LEFT, ONE_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_CHAR, LEFT, ONE_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_CHAR, LEFT, ZERO_CHAR, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void shortSuccessTest() {
        Arguments.checkLessThan(ZERO_SHORT, LEFT, ONE_SHORT, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT);
        Arguments.checkEquals(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT);
        Arguments.checkNotEquals(ZERO_SHORT, LEFT, ONE_SHORT, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT);
        Arguments.checkGreaterThan(ONE_SHORT, LEFT, ZERO_SHORT, RIGHT);
    }

    @Test
    void shortFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_SHORT, LEFT, ZERO_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_SHORT, LEFT, ONE_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_SHORT, LEFT, ONE_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_SHORT, LEFT, ZERO_SHORT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void intSuccessTest() {
        Arguments.checkLessThan(ZERO_INT, LEFT, ONE_INT, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_INT, LEFT, ZERO_INT, RIGHT);
        Arguments.checkEquals(ZERO_INT, LEFT, ZERO_INT, RIGHT);
        Arguments.checkNotEquals(ZERO_INT, LEFT, ONE_INT, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_INT, LEFT, ZERO_INT, RIGHT);
        Arguments.checkGreaterThan(ONE_INT, LEFT, ZERO_INT, RIGHT);
    }

    @Test
    void intFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_INT, LEFT, ZERO_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_INT, LEFT, ZERO_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_INT, LEFT, ONE_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_INT, LEFT, ZERO_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_INT, LEFT, ONE_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_INT, LEFT, ZERO_INT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void longSuccessTest() {
        Arguments.checkLessThan(ZERO_LONG, LEFT, ONE_LONG, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_LONG, LEFT, ZERO_LONG, RIGHT);
        Arguments.checkEquals(ZERO_LONG, LEFT, ZERO_LONG, RIGHT);
        Arguments.checkNotEquals(ZERO_LONG, LEFT, ONE_LONG, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_LONG, LEFT, ZERO_LONG, RIGHT);
        Arguments.checkGreaterThan(ONE_LONG, LEFT, ZERO_LONG, RIGHT);
    }

    @Test
    void longFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_LONG, LEFT, ZERO_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_LONG, LEFT, ZERO_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_LONG, LEFT, ONE_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_LONG, LEFT, ZERO_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_LONG, LEFT, ONE_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_LONG, LEFT, ZERO_LONG, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void bigIntegerSuccessTest() {
        Arguments.checkLessThan(BigInteger.ZERO, LEFT, BigInteger.ONE, RIGHT);
        Arguments.checkLessThanOrEqual(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT);
        Arguments.checkEquals(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT);
        Arguments.checkNotEquals(BigInteger.ZERO, LEFT, BigInteger.ONE, RIGHT);
        Arguments.checkGreaterThanOrEqual(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT);
        Arguments.checkGreaterThan(BigInteger.ONE, LEFT, BigInteger.ZERO, RIGHT);
    }

    @Test
    void bigIntegerFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) >= right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(BigInteger.ONE, LEFT, BigInteger.ZERO, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1) > right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(BigInteger.ZERO, LEFT, BigInteger.ONE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) != right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) == right (=0) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(BigInteger.ZERO, LEFT, BigInteger.ONE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) < right (=1) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(BigInteger.ZERO, LEFT, BigInteger.ZERO, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0) <= right (=0) not allowed!");
    }

    @Test
    void floatSuccessTest() {
        Arguments.checkLessThan(ZERO_FLOAT, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT);
        Arguments.checkEquals(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT);
        Arguments.checkEquals(Float.POSITIVE_INFINITY, LEFT, Float.POSITIVE_INFINITY, RIGHT);
        Arguments.checkEquals(Float.NEGATIVE_INFINITY, LEFT, Float.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(Float.NaN, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Float.NaN, RIGHT);
        Arguments.checkNotEquals(Float.NaN, LEFT, Float.POSITIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Float.POSITIVE_INFINITY, LEFT, Float.NaN, RIGHT);
        Arguments.checkNotEquals(Float.NaN, LEFT, Float.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Float.NEGATIVE_INFINITY, LEFT, Float.NaN, RIGHT);
        Arguments.checkNotEquals(Float.NaN, LEFT, Float.NaN, RIGHT);
        Arguments.checkNotEquals(Float.POSITIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Float.POSITIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Float.NEGATIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Float.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Float.POSITIVE_INFINITY, LEFT, Float.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Float.NEGATIVE_INFINITY, LEFT, Float.POSITIVE_INFINITY, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT);
        Arguments.checkGreaterThan(ONE_FLOAT, LEFT, ZERO_FLOAT, RIGHT);
    }

    @Test
    void floatFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) >= right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_FLOAT, LEFT, ZERO_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1.000000) > right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) == right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_FLOAT, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) < right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_FLOAT, LEFT, ZERO_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) <= right (=0.000000) not allowed!");
    }

    @Test
    void floatFailureNanTest() {
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NaN, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Float.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NaN, LEFT, Float.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NaN, LEFT, Float.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.POSITIVE_INFINITY, LEFT, Float.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NaN, LEFT, Float.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NEGATIVE_INFINITY, LEFT, Float.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=NaN) not allowed!");
    }

    @Test
    void floatFailureInfinityTest() {
        assertThatThrownBy(
                        () -> Arguments.checkNotEquals(Float.POSITIVE_INFINITY, LEFT, Float.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) == right (=Infinity) not allowed!");
        assertThatThrownBy(
                        () -> Arguments.checkNotEquals(Float.NEGATIVE_INFINITY, LEFT, Float.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) == right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.POSITIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Float.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NEGATIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Float.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.POSITIVE_INFINITY, LEFT, Float.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Float.NEGATIVE_INFINITY, LEFT, Float.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=Infinity) not allowed!");
    }

    @Test
    void doubleSuccessTest() {
        Arguments.checkLessThan(ZERO_DOUBLE, LEFT, ONE_DOUBLE, RIGHT);
        Arguments.checkLessThanOrEqual(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT);
        Arguments.checkEquals(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT);
        Arguments.checkEquals(Double.POSITIVE_INFINITY, LEFT, Double.POSITIVE_INFINITY, RIGHT);
        Arguments.checkEquals(Double.NEGATIVE_INFINITY, LEFT, Double.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(ZERO_DOUBLE, LEFT, ONE_DOUBLE, RIGHT);
        Arguments.checkNotEquals(Double.NaN, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Double.NaN, RIGHT);
        Arguments.checkNotEquals(Double.NaN, LEFT, Double.POSITIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Double.POSITIVE_INFINITY, LEFT, Double.NaN, RIGHT);
        Arguments.checkNotEquals(Double.NaN, LEFT, Double.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Double.NEGATIVE_INFINITY, LEFT, Double.NaN, RIGHT);
        Arguments.checkNotEquals(Double.NaN, LEFT, Double.NaN, RIGHT);
        Arguments.checkNotEquals(Double.POSITIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Double.POSITIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Double.NEGATIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT);
        Arguments.checkNotEquals(ZERO_FLOAT, LEFT, Double.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Double.POSITIVE_INFINITY, LEFT, Double.NEGATIVE_INFINITY, RIGHT);
        Arguments.checkNotEquals(Double.NEGATIVE_INFINITY, LEFT, Double.POSITIVE_INFINITY, RIGHT);
        Arguments.checkGreaterThanOrEqual(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT);
        Arguments.checkGreaterThan(ONE_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT);
    }

    @Test
    void doubleFailureTest() {
        assertThatThrownBy(() -> Arguments.checkLessThan(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) >= right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkLessThanOrEqual(ONE_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=1.000000) > right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_DOUBLE, LEFT, ONE_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) == right (=0.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThanOrEqual(ZERO_DOUBLE, LEFT, ONE_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) < right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkGreaterThan(ZERO_DOUBLE, LEFT, ZERO_DOUBLE, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) <= right (=0.000000) not allowed!");
    }

    @Test
    void doubleFailureNanTest() {
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NaN, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Double.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NaN, LEFT, Double.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.POSITIVE_INFINITY, LEFT, Double.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NaN, LEFT, Double.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NEGATIVE_INFINITY, LEFT, Double.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=NaN) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NaN, LEFT, Double.NaN, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=NaN) != right (=NaN) not allowed!");
    }

    @Test
    void doubleFailureInfinityTest() {
        assertThatThrownBy(
                        () -> Arguments.checkNotEquals(Double.POSITIVE_INFINITY, LEFT, Double.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) == right (=Infinity) not allowed!");
        assertThatThrownBy(
                        () -> Arguments.checkNotEquals(Double.NEGATIVE_INFINITY, LEFT, Double.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) == right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.POSITIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Double.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NEGATIVE_INFINITY, LEFT, ONE_FLOAT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=1.000000) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_FLOAT, LEFT, Double.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=0.000000) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.POSITIVE_INFINITY, LEFT, Double.NEGATIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=Infinity) != right (=-Infinity) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(Double.NEGATIVE_INFINITY, LEFT, Double.POSITIVE_INFINITY, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("left (=-Infinity) != right (=Infinity) not allowed!");
    }

    @Test
    void objectSuccessTest() {
        Arguments.checkEquals(ZERO_OBJECT, LEFT, ZERO_OBJECT, RIGHT);
        Arguments.checkEquals(ONE_OBJECT, LEFT, ONE_OBJECT, RIGHT);
        Arguments.checkEquals(NULL_OBJECT, LEFT, NULL_OBJECT, RIGHT);
        Arguments.checkNotEquals(ZERO_OBJECT, LEFT, ONE_OBJECT, RIGHT);
        Arguments.checkNotEquals(ONE_OBJECT, LEFT, ZERO_OBJECT, RIGHT);
        Arguments.checkNotEquals(ZERO_OBJECT, LEFT, NULL_OBJECT, RIGHT);
        Arguments.checkNotEquals(NULL_OBJECT, LEFT, ONE_OBJECT, RIGHT);
        Arguments.checkNull(NULL_OBJECT, LEFT);
        Arguments.checkNotNull(ZERO_OBJECT, LEFT);
    }

    @Test
    void objectFailureTest() {
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_OBJECT, LEFT, ONE_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching(
                        "left \\(=java.lang.Object@[0-9a-fA-F]+\\) != right \\(=java.lang.Object@[0-9a-fA-F]+\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ONE_OBJECT, LEFT, ZERO_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching(
                        "left \\(=java.lang.Object@[0-9a-fA-F]+\\) != right \\(=java.lang.Object@[0-9a-fA-F]+\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(ZERO_OBJECT, LEFT, NULL_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("left \\(=java.lang.Object@[0-9a-fA-F]+\\) != right \\(=null\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkEquals(NULL_OBJECT, LEFT, ONE_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("left \\(=null\\) != right \\(=java.lang.Object@[0-9a-fA-F]+\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ZERO_OBJECT, LEFT, ZERO_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching(
                        "left \\(=java.lang.Object@[0-9a-fA-F]+\\) == right \\(=java.lang.Object@[0-9a-fA-F]+\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(ONE_OBJECT, LEFT, ONE_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching(
                        "left \\(=java.lang.Object@[0-9a-fA-F]+\\) == right \\(=java.lang.Object@[0-9a-fA-F]+\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotEquals(NULL_OBJECT, LEFT, NULL_OBJECT, RIGHT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("left \\(=null\\) == right \\(=null\\) not allowed!");
        assertThatThrownBy(() -> Arguments.checkNull(ZERO_OBJECT, LEFT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("left \\(=java.lang.Object@[0-9a-fA-F]+\\) != null not allowed!");
        assertThatThrownBy(() -> Arguments.checkNotNull(NULL_OBJECT, LEFT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("left \\(=null\\) == null not allowed!");
    }
}
