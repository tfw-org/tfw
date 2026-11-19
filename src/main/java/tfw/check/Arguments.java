package tfw.check;

import static java.util.Collections.unmodifiableMap;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

/**
 * Provides static checks for argument values and relationships between arguments.
 *
 * <p>Each check returns normally unless its failure condition is met, in which case it
 * throws {@link IllegalArgumentException}. Failure messages include operand values and
 * the supplied operand names.
 *
 * <p>Comparison checks support the primitive numeric types and {@link BigInteger}.
 * Equality checks additionally support {@code boolean} and {@link Object}. Overloads
 * accepting both operand names are intended for comparisons between named values.
 * Overloads omitting {@code rightName} are intended for comparisons against constants
 * or other self-explanatory values; the right operand's value appears without a name
 * in the failure message.
 *
 * <p>Floating-point checks use Java primitive comparison operators. Consequently,
 * relational checks do not reject a comparison involving {@code NaN}: the inverse
 * comparison used to detect failure is also {@code false}. {@code NaN} is not equal
 * to itself, so {@code checkEquals} rejects two {@code NaN} values and
 * {@code checkNotEquals} accepts them.
 *
 * <p>Relational checks involving {@link BigInteger} require both operands to be
 * non-null. BigInteger equality checks allow null operands and consider two null
 * operands equal. Object equality checks use the left operand's
 * {@link Object#equals(Object)} method; two null operands are considered equal.
 *
 * <p>This class is not instantiable.
 */
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

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(byte left, String leftName, byte right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(char left, String leftName, char right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(double left, String leftName, double right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(float left, String leftName, float right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(int left, String leftName, int right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(long left, String leftName, long right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left < right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left >= right}
     */
    public static void checkLessThan(short left, String leftName, short right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left.compareTo(right) < 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) >= 0}, or either operand is null
     */
    public static void checkLessThan(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.LESS_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than the right operand.
     *
     * <p>The check's comparison is {@code left.compareTo(right) < 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) >= 0}, or either operand is null
     */
    public static void checkLessThan(BigInteger left, String leftName, BigInteger right) {
        checkLessThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(byte left, String leftName, byte right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(char left, String leftName, char right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(double left, String leftName, double right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(float left, String leftName, float right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(int left, String leftName, int right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(long left, String leftName, long right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left <= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left > right}
     */
    public static void checkLessThanOrEqual(short left, String leftName, short right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) <= 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) > 0}, or either operand is null
     */
    public static void checkLessThanOrEqual(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.LESS_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is less than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) <= 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) > 0}, or either operand is null
     */
    public static void checkLessThanOrEqual(BigInteger left, String leftName, BigInteger right) {
        checkLessThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(byte left, String leftName, byte right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(char left, String leftName, char right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(double left, String leftName, double right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(float left, String leftName, float right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(int left, String leftName, int right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(long left, String leftName, long right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(short left, String leftName, short right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code Objects.equals(left, right)}.
     * Equality is null-safe and compares BigInteger values; two null operands are equal.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code !Objects.equals(left, right)}
     */
    public static void checkEquals(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code Objects.equals(left, right)}.
     * Equality is null-safe and compares BigInteger values; two null operands are equal.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code !Objects.equals(left, right)}
     */
    public static void checkEquals(BigInteger left, String leftName, BigInteger right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(boolean left, String leftName, boolean right, String rightName) {
        checkBoolean(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left == right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left != right}
     */
    public static void checkEquals(boolean left, String leftName, boolean right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code Objects.equals(left, right)}.
     * Equality is determined by the left operand's {@link Object#equals(Object)} method; two null operands are equal.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code !Objects.equals(left, right)}
     */
    public static void checkEquals(Object left, String leftName, Object right, String rightName) {
        checkObjectEquals(Operation.EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code Objects.equals(left, right)}.
     * Equality is determined by the left operand's {@link Object#equals(Object)} method; two null operands are equal.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code !Objects.equals(left, right)}
     */
    public static void checkEquals(Object left, String leftName, Object right) {
        checkEquals(left, leftName, right, null);
    }

    /**
     * Checks that the specified boolean value is true.
     *
     * <p>For ordinary values, the required relation is {@code bool == true}.
     *
     * @param bool the boolean value to check
     * @param boolName the name used to identify the boolean value in a failure message
     * @throws IllegalArgumentException if {@code bool} is false
     */
    public static void checkTrue(boolean bool, String boolName) {
        checkBoolean(Operation.EQUALS, bool, boolName, true, null);
    }

    /**
     * Checks that the specified boolean value is false.
     *
     * <p>For ordinary values, the required relation is {@code bool == false}.
     *
     * @param bool the boolean value to check
     * @param boolName the name used to identify the boolean value in a failure message
     * @throws IllegalArgumentException if {@code bool} is true
     */
    public static void checkFalse(boolean bool, String boolName) {
        checkBoolean(Operation.EQUALS, bool, boolName, false, null);
    }

    /**
     * Checks that the specified object reference is null.
     *
     * <p>For ordinary values, the required relation is {@code object == null}.
     *
     * @param object the object reference to check
     * @param objectName the name used to identify the object in a failure message
     * @throws IllegalArgumentException if {@code object} is not null
     */
    public static void checkNull(Object object, String objectName) {
        checkObjectNull(Operation.IS_NULL, object, objectName);
    }

    /**
     * Checks that the specified object reference is not null.
     *
     * <p>For ordinary values, the required relation is {@code object != null}.
     *
     * @param object the object reference to check
     * @param objectName the name used to identify the object in a failure message
     * @throws IllegalArgumentException if {@code object} is null
     */
    public static void checkNotNull(Object object, String objectName) {
        checkObjectNull(Operation.IS_NOT_NULL, object, objectName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(byte left, String leftName, byte right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(char left, String leftName, char right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(double left, String leftName, double right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(float left, String leftName, float right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(int left, String leftName, int right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(long left, String leftName, long right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(short left, String leftName, short right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code !Objects.equals(left, right)}.
     * Equality is null-safe and compares BigInteger values; two null operands are equal.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code Objects.equals(left, right)}
     */
    public static void checkNotEquals(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code !Objects.equals(left, right)}.
     * Equality is null-safe and compares BigInteger values; two null operands are equal.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code Objects.equals(left, right)}
     */
    public static void checkNotEquals(BigInteger left, String leftName, BigInteger right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(boolean left, String leftName, boolean right, String rightName) {
        checkBoolean(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left != right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left == right}
     */
    public static void checkNotEquals(boolean left, String leftName, boolean right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code !Objects.equals(left, right)}.
     * Equality is determined by the left operand's {@link Object#equals(Object)} method; two null operands are equal.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code Objects.equals(left, right)}
     */
    public static void checkNotEquals(Object left, String leftName, Object right, String rightName) {
        checkObjectEquals(Operation.NOT_EQUALS, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is not equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code !Objects.equals(left, right)}.
     * Equality is determined by the left operand's {@link Object#equals(Object)} method; two null operands are equal.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code Objects.equals(left, right)}
     */
    public static void checkNotEquals(Object left, String leftName, Object right) {
        checkNotEquals(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(byte left, String leftName, byte right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(char left, String leftName, char right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(double left, String leftName, double right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(float left, String leftName, float right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(int left, String leftName, int right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(long left, String leftName, long right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left >= right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left < right}
     */
    public static void checkGreaterThanOrEqual(short left, String leftName, short right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) >= 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) < 0}, or either operand is null
     */
    public static void checkGreaterThanOrEqual(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.GREATER_THAN_OR_EQUAL, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than or equal to the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) >= 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) < 0}, or either operand is null
     */
    public static void checkGreaterThanOrEqual(BigInteger left, String leftName, BigInteger right) {
        checkGreaterThanOrEqual(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(byte left, String leftName, byte right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(byte left, String leftName, byte right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(char left, String leftName, char right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(char left, String leftName, char right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(double left, String leftName, double right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(double left, String leftName, double right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(float left, String leftName, float right, String rightName) {
        checkFloatingPoint(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * Floating-point behavior follows Java operators, including their behavior for {@code NaN}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(float left, String leftName, float right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(int left, String leftName, int right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(int left, String leftName, int right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(long left, String leftName, long right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(long left, String leftName, long right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(short left, String leftName, short right, String rightName) {
        checkIntegral(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left > right}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left <= right}
     */
    public static void checkGreaterThan(short left, String leftName, short right) {
        checkGreaterThan(left, leftName, right, null);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) > 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * If {@code rightName} is null, the failure message shows the right operand's value without a name.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @param rightName the right operand's name in a failure message; may be null
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) <= 0}, or either operand is null
     */
    public static void checkGreaterThan(BigInteger left, String leftName, BigInteger right, String rightName) {
        checkBigInteger(Operation.GREATER_THAN, left, leftName, right, rightName);
    }

    /**
     * Checks that the left operand is greater than the right operand.
     *
     * <p>For ordinary values, the required relation is {@code left.compareTo(right) > 0}.
     * BigInteger operands are compared by value using {@link BigInteger#compareTo(BigInteger)}.
     * This overload is intended for a constant or otherwise self-explanatory right operand.
     * The right operand's name is omitted from the failure message.
     *
     * @param left the left operand
     * @param leftName the name used to identify the left operand in a failure message
     * @param right the right operand
     * @throws IllegalArgumentException if the failure condition {@code left.compareTo(right) <= 0}, or either operand is null
     */
    public static void checkGreaterThan(BigInteger left, String leftName, BigInteger right) {
        checkGreaterThan(left, leftName, right, null);
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
            if (rightName == null) {
                final String msg = String.format(
                        "%s (=%d) %s %d not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation), right);

                throw new IllegalArgumentException(msg);
            } else {
                final String msg = String.format(
                        "%s (=%d) %s %s (=%d) not allowed!",
                        leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

                throw new IllegalArgumentException(msg);
            }
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
            if (rightName == null) {
                final String msg = String.format(
                        "%s (=%f) %s %f not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation), right);

                throw new IllegalArgumentException(msg);
            } else {
                final String msg = String.format(
                        "%s (=%f) %s %s (=%f) not allowed!",
                        leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

                throw new IllegalArgumentException(msg);
            }
        }
    }

    private static void checkBigInteger(
            final Operation operation,
            final BigInteger left,
            final String leftName,
            final BigInteger right,
            final String rightName) {
        if (operation != Operation.EQUALS && operation != Operation.NOT_EQUALS) {
            if (left == null) {
                final String msg = String.format("%s == null not allowed!", leftName == null ? "left" : leftName);

                throw new IllegalArgumentException(msg);
            }

            if (right == null) {
                final String msg = String.format("%s == null not allowed!", rightName == null ? "right" : rightName);

                throw new IllegalArgumentException(msg);
            }
        }

        if ((operation == Operation.EQUALS || operation == Operation.NOT_EQUALS) && (left == null || right == null)) {
            checkObjectEquals(operation, left, leftName, right, rightName);

            return;
        }

        final int result = left.compareTo(right);

        if ((operation == Operation.LESS_THAN && result >= 0)
                || (operation == Operation.LESS_THAN_OR_EQUAL && result > 0)
                || (operation == Operation.EQUALS && result != 0)
                || (operation == Operation.NOT_EQUALS && result == 0)
                || (operation == Operation.GREATER_THAN_OR_EQUAL && result < 0)
                || (operation == Operation.GREATER_THAN && result <= 0)) {
            if (rightName == null) {
                final String msg = String.format(
                        "%s (=%s) %s %s not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation), right);

                throw new IllegalArgumentException(msg);
            } else {
                final String msg = String.format(
                        "%s (=%s) %s %s (=%s) not allowed!",
                        leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

                throw new IllegalArgumentException(msg);
            }
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

    private static void checkObjectEquals(
            final Operation operation,
            final Object left,
            final String leftName,
            final Object right,
            final String rightName) {
        final boolean equals = (left == null && right == null) || (left != null && left.equals(right));

        if ((operation == Operation.EQUALS && !equals) || (operation == Operation.NOT_EQUALS && equals)) {
            if (rightName == null) {
                final String msg = String.format(
                        "%s (=%s) %s %s not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation), right);

                throw new IllegalArgumentException(msg);
            } else {
                final String msg = String.format(
                        "%s (=%s) %s %s (=%s) not allowed!",
                        leftName, left, OPERATION_STRING_MAP.get(operation), rightName, right);

                throw new IllegalArgumentException(msg);
            }
        }
    }

    private static void checkObjectNull(final Operation operation, final Object left, final String leftName) {
        if ((operation == Operation.IS_NULL && left != null) || (operation == Operation.IS_NOT_NULL && left == null)) {
            final String msg =
                    String.format("%s (=%s) %s null not allowed!", leftName, left, OPERATION_STRING_MAP.get(operation));

            throw new IllegalArgumentException(msg);
        }
    }
}
