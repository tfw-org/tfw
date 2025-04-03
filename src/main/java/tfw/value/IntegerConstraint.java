package tfw.value;

/**
 * An integer value constraint.
 */
public class IntegerConstraint extends RangeConstraint<Integer> {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public IntegerConstraint(int min, int max) {
        super(Integer.class, Integer.valueOf(min), Integer.valueOf(max), true, true);
    }

    @Override
    protected int compareTo(Integer left, Integer right) {
        return left.compareTo(right);
    }
}
