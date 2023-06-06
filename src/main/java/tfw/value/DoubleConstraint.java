package tfw.value;

/**
 * An double value constraint.
 */
public class DoubleConstraint extends RangeConstraint {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
     */
    public DoubleConstraint(double min, double max, boolean minInclusive, boolean maxInclusive) {
        super(Double.class, new Double(min), new Double(max), minInclusive, maxInclusive);
    }
}
