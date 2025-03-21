package tfw.check;

/**
 * A utility for checking arguements to methods and constructors.
 *
 */
public class Argument {
    private static final String ASSERT_NOT_NULL_FORMAT = "%s == null not allowed!";
    private static final String ASSERT_LENGTH_NOT_ZERO_FORMAT = "%s.length == 0 not allowed!";
    private static final String ASSERT_NOT_EMPTY_FORMAT = "%s.isEmpty() not allowed!";
    private static final String ASSERT_ELEMENTS_NOT_NULL_FORMAT = "%s[%d] == null not allowed!";
    private static final String ASSERT_GREATER_THAN_FORMAT = "%s (=%d) <= %d not allowed!";
    private static final String ASSERT_GREATER_THAN_OR_EQUAL_TO_I_FORMAT = "%s (=%d) < %d not allowed!";
    private static final String ASSERT_GREATER_THAN_OR_EQUAL_TO_F_FORMAT = "%s (=%f) < %f not allowed!";
    private static final String ASSERT_EQUALS_FORMAT = "%s (=%d) != %s (=%d) not allowed!";
    private static final String ASSERT_NOT_EQUALS_FORMAT = "%s (=%d) == %d not allowed!";
    private static final String ASSERT_NOT_GREATER_THAN_ILR_FORMAT = "%s (=%d) > %s (=%d) not allowed!";
    private static final String ASSERT_NOT_GREATER_THAN_FLR_FORMAT = "%s (=%f) > %s (=%f) not allowed!";
    private static final String ASSERT_NOT_GREATER_THAN_IVC_FORMAT = "%s (=%d) > %d not allowed!";
    private static final String ASSERT_NOT_GREATER_THAN_FVC_FORMAT = "%s (=%f) > %f not allowed!";
    private static final String ASSERT_LESS_THAN_I_FORMAT = "%s (=%d) >= %s (=%d) not allowed!";
    private static final String ASSERT_LESS_THAN_F_FORMAT = "%s (=%f) >= %s (=%f) not allowed!";
    private static final String ASSERT_INSTANCE_OF_FORMAT = "%s is not an instance of %s!";

    private Argument() {}

    /**
     * Checks the argument for a null value.
     *
     * @param argument the argument to be checked.
     * @param argumentName the name of the argument.
     * @throws IllegalArgumentException if <code>argument == null</code>.
     */
    public static void assertNotNull(Object argument, String argumentName) {
        if (argument == null) {
            final String msg = String.format(ASSERT_NOT_NULL_FORMAT, argumentName);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks the array argument for being null or zero length.
     *
     * @param argument the argument to be checked.
     * @param argumentName the name of the argument.
     * @throws IllegalArgumentException if <code>argument == null</code>.
     * @throws IllegalArgumentException if <code>argument.length == 0</code>.
     */
    public static void assertNotEmpty(Object[] argument, String argumentName) {
        assertNotNull(argument, argumentName);

        if (argument.length == 0) {
            final String msg = String.format(ASSERT_LENGTH_NOT_ZERO_FORMAT, argumentName);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks the string argument for zero length.
     *
     * @param argument the argument to be checked.
     * @param argumentName the name of the argument.
     * @throws IllegalArgumentException if <code>argument == null</code>.
     * @throws IllegalArgumentException if <code>argument.isEmpty()</code>.
     */
    public static void assertNotEmpty(String argument, String argumentName) {
        assertNotNull(argument, argumentName);

        if (argument.isEmpty()) {
            final String msg = String.format(ASSERT_NOT_EMPTY_FORMAT, argumentName);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks the array argument for null elements.
     *
     * @param argument the argument to be checked.
     * @param argumentName the name of the argument.
     * @throws IllegalArgumentException if <code>argument == null</code>.
     * @throws IllegalArgumentException if <code>argument[i] == null</code>.
     */
    public static void assertElementNotNull(Object[] argument, String argumentName) {
        assertNotNull(argument, argumentName);

        for (int i = 0; i < argument.length; i++) {
            if (argument[i] == null) {
                final String msg = String.format(ASSERT_ELEMENTS_NOT_NULL_FORMAT, argumentName, i);

                throw new IllegalArgumentException(msg);
            }
        }
    }

    /**
     * Checks that the argument is greater than a constant.
     *
     * @param argument The argument to compare.
     * @param constant The constant to compare.
     * @param argumentName The name of the argument.
     * @throws IllegalArgumentException if <code>argument &lt;= constant</code>
     */
    public static final void assertGreaterThan(int argument, int constant, String argumentName) {
        if (argument <= constant) {
            final String msg = String.format(ASSERT_GREATER_THAN_FORMAT, argumentName, argument, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the argument is greater than or equal to a constant.
     *
     * @param argument The argument to compare
     * @param constant The constant to compare
     * @param argumentName The name of the argument.
     * @throws IllegalArgumentException if <code>argument &lt; constant</code>
     */
    public static final void assertGreaterThanOrEqualTo(int argument, int constant, String argumentName) {
        if (argument < constant) {
            final String msg =
                    String.format(ASSERT_GREATER_THAN_OR_EQUAL_TO_I_FORMAT, argumentName, argument, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the argument is greater than or equal to a constant.
     *
     * @param argument The argument to compare
     * @param constant The constant to compare
     * @param argumentName The name of the argument.
     * @throws IllegalArgumentException if <code>argument &lt; constant</code>
     */
    public static final void assertGreaterThanOrEqualTo(long argument, long constant, String argumentName) {
        if (argument < constant) {
            final String msg =
                    String.format(ASSERT_GREATER_THAN_OR_EQUAL_TO_I_FORMAT, argumentName, argument, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left and right arguments are equal.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertEquals(int left, int right, String leftName, String rightName) {
        if (left != right) {
            final String msg = String.format(ASSERT_EQUALS_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left and right arguments are equal.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertEquals(long left, long right, String leftName, String rightName) {
        if (left != right) {
            final String msg = String.format(ASSERT_EQUALS_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left argument is less than or equal to right argument.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertNotGreaterThan(long left, long right, String leftName, String rightName) {
        if (left > right) {
            final String msg = String.format(ASSERT_NOT_GREATER_THAN_ILR_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is less than or equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertNotGreaterThan(int value, int constant, String valueName) {
        if (value > constant) {
            final String msg = String.format(ASSERT_NOT_GREATER_THAN_IVC_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is less than or equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertNotGreaterThan(double value, double constant, String valueName) {
        if (value > constant) {
            final String msg = String.format(ASSERT_NOT_GREATER_THAN_FVC_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is greater than or equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertNotLessThan(int value, int constant, String valueName) {
        if (value < constant) {
            final String msg = String.format(ASSERT_GREATER_THAN_OR_EQUAL_TO_I_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is greater than or equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertNotLessThan(long value, long constant, String valueName) {
        if (value < constant) {
            final String msg = String.format(ASSERT_GREATER_THAN_OR_EQUAL_TO_I_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is greater than or equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertGreaterThanOrEqualTo(double value, double constant, String valueName) {
        if (value < constant) {
            final String msg = String.format(ASSERT_GREATER_THAN_OR_EQUAL_TO_F_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left argument is greater than or equal to right argument.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertLessThan(long left, long right, String leftName, String rightName) {
        if (left >= right) {
            final String msg = String.format(ASSERT_LESS_THAN_I_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left argument is greater than or equal to right argument.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertLessThan(double left, double right, String leftName, String rightName) {
        if (left >= right) {
            final String msg = String.format(ASSERT_LESS_THAN_F_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the value is not equal to a constant.
     *
     * @param value The value to compare
     * @param constant The constant to compare
     * @param valueName The name of the value.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertNotEquals(int value, int constant, String valueName) {
        if (value == constant) {
            final String msg = String.format(ASSERT_NOT_EQUALS_FORMAT, valueName, value, constant);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the left argument is less than or equal to right argument.
     *
     * @param left argument to compare.
     * @param right argument to compare.
     * @param leftName name of left argument being compared.
     * @param rightName name of right argument being compared.
     */
    public static final void assertNotGreaterThan(double left, double right, String leftName, String rightName) {
        if (left > right) {
            final String msg = String.format(ASSERT_NOT_GREATER_THAN_FLR_FORMAT, leftName, left, rightName, right);

            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Checks that the object is an instance of the clazz.
     *
     * @param clazz The desired class the object should be an instance of.
     * @param object The object to check.
     * @param objectName The name of the object to check.
     * @throws IllegalArgumentException if <code>value &lt; constant</code>
     */
    public static final void assertInstanceOf(Class<?> clazz, Object object, String objectName) {
        if (!clazz.isInstance(object)) {
            final String msg = String.format(ASSERT_INSTANCE_OF_FORMAT, objectName, clazz.getName());

            throw new IllegalArgumentException(msg);
        }
    }
}
