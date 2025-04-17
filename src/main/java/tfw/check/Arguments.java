package tfw.check;

import static java.util.Collections.unmodifiableMap;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

public final class Arguments {
    private Arguments() {}

    private enum Operation {
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        EQUALS,
        IS_NULL,
        IS_NOT_NULL,
        NOT_EQUALS,
        GREATER_THAN_OR_EQUAL,
        GREATER_THAN
    }

    private static final Map<Operation, String> OPERATION_STRING_MAP;

    static {
        final Map<Operation, String> m = new EnumMap<>(Operation.class);

        m.put(Operation.LESS_THAN, ">=");
        m.put(Operation.LESS_THAN_OR_EQUAL, ">");
        m.put(Operation.EQUALS, "!=");
        m.put(Operation.IS_NULL, "!=");
        m.put(Operation.IS_NOT_NULL, "==");
        m.put(Operation.NOT_EQUALS, "==");
        m.put(Operation.GREATER_THAN_OR_EQUAL, "<");
        m.put(Operation.GREATER_THAN, "<=");

        OPERATION_STRING_MAP = unmodifiableMap(m);
    }

    public static void checkLessThan(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThan(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkLessThanOrEqual(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkEquals(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkEquals(boolean left, String leftName, boolean right, String rightName) {
        checkBoolean(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkTrue(boolean bool, String boolName) {
        checkBoolean(Operation.EQUALS, bool, boolName, true, null);
    }

    public static void checkFalse(boolean bool, String boolName) {
        checkBoolean(Operation.EQUALS, bool, boolName, false, null);
    }

    public static void checkEquals(Object left, String leftName, Object right, String rightName) {
        checkObject(Operation.EQUALS, left, leftName, right, rightName);
    }

    public static void checkNull(Object object, String objectName) {
        checkObject(Operation.IS_NULL, object, objectName, null, null);
    }

    public static void checkNotNull(Object object, String objectName) {
        checkObject(Operation.IS_NOT_NULL, object, objectName, null, null);
    }

    public static void checkNotEquals(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(boolean left, String leftName, boolean right, String rightName) {
        checkBoolean(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkNotEquals(Object left, String leftName, Object right, String rightName) {
        checkObject(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThanOrEqual(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    public static void checkGreaterThan(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    private static void checkIntegral(
            final Operation operation,
            final long left,
            final String leftName,
            final long right,
            final String rightName) {
        if ((operation == Operation.LESS_THAN && left >= right)
                || (operation == Operation.LESS_THAN_OR_EQUAL && left > right)
                || (operation == Operation.EQUALS && left != right)
                || (operation == Operation.NOT_EQUALS && left == right)
                || (operation == Operation.GREATER_THAN_OR_EQUAL && left < right)
                || (operation == Operation.GREATER_THAN && left <= right)) {
            final String msg = String.format(
                    "%s (=%d) %s %s (=%d) not allowed!",
                    leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkFloatingPoint(
            final Operation operation,
            final double left,
            final String leftName,
            final double right,
            final String rightName) {
        if ((operation == Operation.LESS_THAN && left >= right)
                || (operation == Operation.LESS_THAN_OR_EQUAL && left > right)
                || (operation == Operation.EQUALS && left != right)
                || (operation == Operation.NOT_EQUALS && left == right)
                || (operation == Operation.GREATER_THAN_OR_EQUAL && left < right)
                || (operation == Operation.GREATER_THAN && left <= right)) {
            final String msg = String.format(
                    "%s (=%f) %s %s (=%f) not allowed!",
                    leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkBigInteger(
            final Operation operation,
            final BigInteger left,
            final String leftName,
            final BigInteger right,
            final String rightName) {
        final int result = left.compareTo(right);

        if ((operation == Operation.LESS_THAN && result >= 0)
                || (operation == Operation.LESS_THAN_OR_EQUAL && result > 0)
                || (operation == Operation.EQUALS && result != 0)
                || (operation == Operation.NOT_EQUALS && result == 0)
                || (operation == Operation.GREATER_THAN_OR_EQUAL && result < 0)
                || (operation == Operation.GREATER_THAN && result <= 0)) {
            final String msg = String.format(
                    "%s (=%s) %s %s (=%s) not allowed!",
                    leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    private static void checkBoolean(
            final Operation operation,
            final boolean left,
            final String leftName,
            final boolean right,
            final String rightName) {
        if ((operation == Operation.EQUALS && left != right) || (operation == Operation.NOT_EQUALS && left == right)) {
            if (rightName == null) {
                final String msg = String.format(
                        "%s (=%b) %s %b not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation), right);

                throw new IllegalArgumentException(msg);
            } else {
                final String msg = String.format(
                        "%s (=%b) %s %s (=%b) not allowed!",
                        leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

                throw new IllegalArgumentException(msg);
            }
        }
    }

    private static void checkObject(
            final Operation operation,
            final Object left,
            final String leftName,
            final Object right,
            final String rightName) {
        if (operation == Operation.EQUALS || operation == Operation.NOT_EQUALS) {
            final boolean equals = (left == null && right == null) || (left != null && left.equals(right));

            if ((operation == Operation.EQUALS && !equals) || (operation == Operation.NOT_EQUALS && equals)) {
                final String leftClazz = left == null ? null : left.getClass().getName();
                final String rightClazz =
                        right == null ? null : right.getClass().getName();
                final String msg = String.format(
                        "%s (=%s) %s %s (=%s) not allowed!",
                        leftName, leftClazz, OPERATION_STRING_MAP.get(operation), rightName, rightClazz);

                throw new IllegalArgumentException(msg);
            }
        } else if ((operation == Operation.IS_NULL && left != null)
                || (operation == Operation.IS_NOT_NULL && left == null)) {
            final String leftClazz = left == null ? null : left.getClass().getName();
            final String msg = String.format(
                    "%s (=%s) %s null not allowed!", leftName, leftClazz, OPERATION_STRING_MAP.get(operation));

            throw new IllegalArgumentException(msg);
        }
    }
}
