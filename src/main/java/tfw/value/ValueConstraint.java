package tfw.value;


/**
 * 
 */
public abstract class ValueConstraint
{
    /** The string used to represent a value which complies with the constraint. */
    public static final String VALID = "Valid";

    /**
     * Checks the value against the constraint and throws an exception if it
     * does not comply.
     * 
     * @param value
     *            The value to be checked.
     * @throws ValueException
     *             if the value does not comply with the constraint.
     */
    public final void checkValue(Object value) throws ValueException
    {
        String valueCompliance = getValueCompliance(value);

        if (valueCompliance != VALID)
        {
            throw new ValueException(valueCompliance);
        }
    }

    /**
     * Returns true if every value which meets the specified constraint also
     * meets this constraint, otherwise returns false. Note that the reverse is
     * not necessarily true.
     * 
     * @param constraint
     *            the constraint to be checked.
     * 
     * @return true if every value which meets the specified constraint also
     *         meets this constraint, otherwise returns false.
     */
    public abstract boolean isCompatible(ValueConstraint constraint);

    /**
     * Returns {@link #VALID} if the value complies with the constraint,
     * otherwise it returns a string indicating why the value does not comply.
     * 
     * @param value
     *            The value to be evaluated.
     * @return {@link #VALID} if the value complies with the constraint,
     *         otherwise it returns a string indicating why the value does not
     *         comply.
     */
    public abstract String getValueCompliance(Object value);

}
