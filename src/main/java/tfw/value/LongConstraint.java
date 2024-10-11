package tfw.value;

/**
 * An integer value constraint.
 */
public class LongConstraint extends RangeConstraint<Long> {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public LongConstraint(long min, long max) {
        super(Long.class, Long.valueOf(min), Long.valueOf(max), true, true);
    }

    @Override
    protected int compareTo(Long left, Long right) {
        return left.compareTo(right);
    }
}
