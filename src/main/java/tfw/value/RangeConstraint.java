package tfw.value;

import tfw.check.Argument;

/**
 * A value constraint which constrains the value to a range based on the
 * <code>java.lang.Comparable</code> interface.
 */
public abstract class RangeConstraint<T extends Number> extends ClassValueConstraint<T> {
    private final T min;
    private final T max;
    private final boolean minInclusive;
    private final boolean maxInclusive;

    /**
     * Creates a constraint with the specified attributes.
     *
     * @param valueType the 'type' of valid values.
     * @param min the minimum value.
     * @param max the maximum value.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
     */
    protected RangeConstraint(Class<T> valueType, T min, T max, boolean minInclusive, boolean maxInclusive) {
        super(valueType);
        Argument.assertNotNull(min, "min");
        Argument.assertNotNull(max, "max");

        int compare = compareTo(min, max);

        if (compare > 0) {
            throw new IllegalArgumentException("min > max not allowed!");
        }

        if ((compare == 0) && !minInclusive && !maxInclusive) {
            throw new IllegalArgumentException("Empty range, min == max and neither are inclusive");
        }

        this.max = max;
        this.min = min;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    /**
     * Returns {@link #VALID} if the value complies with the constraint,
     * otherwise it returns a string indicating why the value does not comply.
     *
     * @param value The value to be evaluated.
     * @return {@link #VALID} if the value complies with the constraint,
     * otherwise it returns a string indicating why the value does not comply.
     */
    public String getValueCompliance(T value) {
        String str = super.getValueCompliance(value);

        if (!VALID.equals(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("value = '").append(value).append("' is out of range, ");

        int minCompare = compareTo(min, value);

        // if value is less than min or equal to min and min is not valid...
        if ((minCompare > 0) || ((minCompare == 0) && !minInclusive)) {
            sb.append("must be greater than ");

            if (minInclusive) {
                sb.append("or equal to ");
            }

            sb.append("'").append(min).append("'");

            return sb.toString();
        }

        int maxCompare = compareTo(max, value);

        // if value is greater than max or equal to max and max is not valid...
        if ((maxCompare < 0) || ((maxCompare == 0) && !maxInclusive)) {
            sb.append("must be less than ");

            if (maxInclusive) {
                sb.append("or equal to ");
            }

            sb.append("'").append(max).append("'");

            return sb.toString();
        }
        return VALID;
    }

    /**
     * Returns true if every value which meets the specified constraint
     * also meets this constraint, otherwise returns false. Note that the
     * reverse is not necessarily true.
     *
     * @param constraint the constraint to be checked.
     *
     * @return true if every value which meets the specified constraint
     * also meets this constraint, otherwise returns false.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean isCompatible(ValueConstraint<?> constraint) {
        if (constraint == this) {
            // if we use a factory to create constraints
            // so that we done have object explosion, this
            // check will work for most cases and be
            // very efficient.
            return true;
        }

        // check constraint type...
        if (!(constraint instanceof RangeConstraint)) {
            return false;
        }

        // check value type...
        if (!super.isCompatible(constraint)) {
            return false;
        }

        RangeConstraint<T> rc = (RangeConstraint<T>) constraint;

        int minCompare = compareTo(min, rc.min);

        // if this min is greater than rc min...
        if (minCompare > 0) {
            return false;
        }

        // if mins are equal...
        if (minCompare == 0 && !this.minInclusive && rc.minInclusive) {
            return false;
        }

        int maxCompare = compareTo(max, rc.max);

        // if rc.max > this.max...
        if (maxCompare < 0) {
            return false;
        }

        // if maxs are equal...
        return !(maxCompare == 0 && !this.maxInclusive && rc.maxInclusive);
    }

    /**
     * Returns a string representation of the constraint.
     * @return a string representation of the constraint.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RangeConstraint[type = ").append(valueType.getName());
        sb.append(", min = ").append(min);
        sb.append(", max = ").append(max);
        sb.append("]");

        return sb.toString();
    }

    protected abstract int compareTo(T left, T right);
}
