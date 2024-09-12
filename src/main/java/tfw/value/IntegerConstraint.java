package tfw.value;

/**
 * An integer value constraint.
 */
public class IntegerConstraint extends RangeConstraint {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public IntegerConstraint(int min, int max) {
        super(Integer.class, new Integer(min), new Integer(max), true, true);
    }
}
