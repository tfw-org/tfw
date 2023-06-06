package tfw.value;

/**
 * An float value constraint.
 */
public class FloatConstraint extends RangeConstraint {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
     */
    public FloatConstraint(float min, float max, boolean minInclusive, boolean maxInclusive) {
        super(Float.class, new Float(min), new Float(max), minInclusive, maxInclusive);
    }
}
