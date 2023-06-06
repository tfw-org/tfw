package tfw.check;

/**
 * A utility for checking arguements to methods and constructors.
 * 
 */
public class Argument
{
    /**
     * Checks the argument for a null value.
     * 
     * @param argument
     *            the argument to be checked.
     * @param argumentName
     *            the name of the argument.
     * 
     * @throws IllegalArgumentException
     *             if <code>argument == null</code>.
     */
    public static void assertNotNull(Object argument, String argumentName)
    {
        if (argument == null)
        {
            throw new IllegalArgumentException(argumentName
                    + " == null not allowed");
        }
    }

    /**
     * Checks the array argument for zero length.
     * 
     * @param argument
     *            the argument to be checked.
     * @param argumentName
     *            the name of the argument.
     * 
     * @throws IllegalArgumentException
     *             if <code>argument == null</code>.
     * @throws IllegalArgumentException
     *             if <code>argument.length == 0</code>.
     */
    public static void assertNotEmpty(Object[] argument, String argumentName)
    {
        assertNotNull(argument, argumentName);

        if (argument.length == 0)
        {
            throw new IllegalArgumentException(argumentName
                    + ".length == 0 not allowed");
        }
    }

    /**
     * Checks the string argument for zero length.
     * 
     * @param argument
     *            the argument to be checked.
     * @param argumentName
     *            the name of the argument.
     * 
     * @throws IllegalArgumentException
     *             if <code>argument == null</code>.
     * @throws IllegalArgumentException
     *             if <code>argument.length() == 0</code>.
     */
    public static void assertNotEmpty(String argument, String argumentName)

    {
        assertNotNull(argument, argumentName);

        if (argument.length() == 0)
        {
            throw new IllegalArgumentException(argumentName
                    + ".length() == 0 not allowed");
        }
    }

    /**
     * Checks the array argument for null elements.
     * 
     * @param argument
     *            the argument to be checked.
     * @param argumentName
     *            the name of the argument.
     * 
     * @throws IllegalArgumentException
     *             if <code>argument == null</code>.
     * @throws IllegalArgumentException
     *             if <code>argument[i] == null</code>.
     */
    public static void assertElementNotNull(Object[] argument,
            String argumentName)
    {
        assertNotNull(argument, argumentName);

        for (int i = 0; i < argument.length; i++)
        {
            if (argument[i] == null)
            {
                throw new IllegalArgumentException(argumentName + "[" + i + "]"
                        + "== null not allowed");
            }
        }
    }

    /**
     * Checks that the argument is greater than a constant.
     * 
     * @param argument
     *            The argument to be checked.
     * @param constant
     *            The constant that the argument must be greater than.
     * @param argumentName
     *            The name of the argument.
     * @throws IllegalArgumentException
     *             if <code>argument &lt;= constant</code>
     */
    public static final void assertGreaterThan(int argument, int constant,
            String argumentName)
    {
        if (argument <= constant)
        {
            throw new IllegalArgumentException(argumentName + " (=" + argument
                    + ") <= " + constant + " not allowed.");
        }
    }

    /**
     * Checks that the argument is greater than or equal to a constant.
     * 
     * @param argument
     *            The argument to be checked.
     * @param constant
     *            The constant that the argument must be greater than.
     * @param argumentName
     *            The name of the argument.
     * @throws IllegalArgumentException
     *             if <code>argument &lt; constant</code>
     */
    public static final void assertGreaterThanOrEqualTo(int argument,
            int constant, String argumentName)
    {
        if (argument < constant)
        {
            throw new IllegalArgumentException(argumentName + " (=" + argument
                    + ") < " + constant + " not allowed.");
        }
    }

    /**
     * Checks that the argument is greater than or equal to a constant.
     * 
     * @param argument
     *            The argument to be checked.
     * @param constant
     *            The constant that the argument must be greater than.
     * @param argumentName
     *            The name of the argument.
     * @throws IllegalArgumentException
     *             if <code>argument &lt; constant</code>
     */
    public static final void assertGreaterThanOrEqualTo(long argument,
            long constant, String argumentName)
    {
        if (argument < constant)
        {
            throw new IllegalArgumentException(argumentName + " (=" + argument
                    + ") < " + constant + " not allowed.");
        }
    }

    public static final void assertEquals(int left, int right, String leftName,
            String rightName)
    {
        if (left != right)
            throw new IllegalArgumentException(leftName + " (=" + left
                    + ") != " + rightName + " (=" + right + ") not allowed!");
    }

    public static final void assertEquals(long left, long right,
            String leftName, String rightName)
    {
        if (left != right)
            throw new IllegalArgumentException(leftName + " (=" + left
                    + ") != " + rightName + " (=" + right + ") not allowed!");
    }

    public static final void assertNotGreaterThan(long left, long right,
            String leftName, String rightName)
    {
        if (left > right)
            throw new IllegalArgumentException(leftName + " (=" + left + ") > "
                    + rightName + " (=" + right + ") not allowed!");
    }

    public static final void assertNotGreaterThan(int value, int constant,
    		String valueName)
    {
    	if (value > constant)
    		throw new IllegalArgumentException(valueName + " (=" + value
    			+ ") > " + constant + " not allowed!");
    }
    
    public static final void assertNotGreaterThan(double value, double constant,
    		String valueName)
    {
    	if (value > constant)
    		throw new IllegalArgumentException(valueName + " (=" + value
    			+ ") > " + constant + " not allowed!");
    }
    
    public static final void assertNotLessThan(int value, int constant,
            String valueName)
    {
        if (value < constant)
            throw new IllegalArgumentException(valueName + " (=" + value
                    + ") < " + constant + " not allowed!");
    }

    public static final void assertNotLessThan(long value, long constant,
            String valueName)
    {
        if (value < constant)
            throw new IllegalArgumentException(valueName + " (=" + value
                    + ") < " + constant + " not allowed!");
    }

    public static final void assertNotLessThan(double value, double constant,
            String valueName)
    {
        if (value < constant)
            throw new IllegalArgumentException(valueName + " (=" + value
                    + ") < " + constant + " not allowed!");
    }

    public static final void assertLessThan(long left, long right,
            String leftName, String rightName)
    {
        if (left >= right)
            throw new IllegalArgumentException(leftName + " (=" + left
                    + ") >= " + rightName + " (=" + right + ") not allowed!");
    }
    
    public static final void assertLessThan(double left, double right,
    		String leftName, String rightName)
    {
        if (left >= right)
            throw new IllegalArgumentException(leftName + " (=" + left
                    + ") >= " + rightName + " (=" + right + ") not allowed!");
    }
    
    public static final void assertNotEquals(int value, int constant,
        String valueName)
    {
        if (value == constant)
            throw new IllegalArgumentException(valueName + " == " + constant
                + " not allowed!");
    }

    public static final void assertNotGreaterThanOrEquals(int left, int right,
        String leftName, String rightName)
    {
        if (left >= right)
            throw new IllegalArgumentException(leftName + " (=" + left
                + ") >= " + rightName + " (=" + right + ") not allowed!");
    }

	public static final void assertNotGreaterThanOrEquals(long left, long right,
			String leftName, String rightName)
	{
		if (left >= right)
			throw new IllegalArgumentException(leftName + " (=" + left
				+ ") >= " + rightName + " (=" + right + ") not allowed!");
	}
	
	public static final void assertNotGreaterThan(double left, double right,
			String leftName, String rightName) {
        if (left > right)
            throw new IllegalArgumentException(leftName + " (=" + left + ") > "
                    + rightName + " (=" + right + ") not allowed!");
	}
}