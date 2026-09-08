package tfw.tsm.ecd;

import tfw.check.Arguments;

public class IntegerRangePredicate extends IsAssignableFromPredicate {
    private final int min;
    private final int max;

    public IntegerRangePredicate(final int min, final int max) {
        super(Integer.class);

        Arguments.checkLessThanOrEqual(min, "min", max, "max");

        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(Object object) {
        if (!super.test(object)) {
            return false;
        }

        final int i = (Integer) object;

        return min <= i && i <= max;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        IntegerRangePredicate other = (IntegerRangePredicate) obj;

        return min == other.min && max == other.max;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + min;
        result = 31 * result + max;
        return result;
    }
}
