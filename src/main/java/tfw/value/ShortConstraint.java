package tfw.value;

/**
 * An short value constraint.
 */
public class ShortConstraint extends RangeConstraint<Short> {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public ShortConstraint(short min, short max) {
        super(Short.class, Short.valueOf(min), Short.valueOf(max), true, true);
    }

    @Override
    protected int compareTo(Short left, Short right) {
        return left.compareTo(right);
    }
}
