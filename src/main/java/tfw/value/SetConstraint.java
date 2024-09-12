package tfw.value;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import tfw.check.Argument;

/**
 * A value constraint where the value must be equal to an element of an explict
 * set of values to be valid. Equality is determined by the
 * <code>equals(Object)</code> method. Note that <code>null</code> is not a
 * valid value.
 */
public class SetConstraint extends ValueConstraint {

    private final Set<Object> validValues;

    /**
     * Creates a contraint with the specified set of valid values.
     *
     * @param validValues
     *            The non-empty list of valid values. Note that
     *            <code>null</code> is not a valid value.
     */
    public SetConstraint(Object[] validValues) {
        Argument.assertNotEmpty(validValues, "validValues");
        Argument.assertElementNotNull(validValues, "validValues");

        this.validValues = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(validValues)));
    }

    /**
     * Returns true if the <code>constraint</code> is an instance of
     * <code>SetConstraint</code> and its set of values is a sub-set of this
     * constraints values.
     *
     * @param constraint
     *            The constraint to check.
     * @return true if the <code>constraint</code> is an instance of
     *         <code>SetConstraint</code> and its set of values is a sub-set
     *         of this constraints values, otherwise returns false.
     */
    public boolean isCompatible(ValueConstraint constraint) {
        if (constraint instanceof SetConstraint) {
            SetConstraint sc = (SetConstraint) constraint;
            return this.validValues.containsAll(sc.validValues);
        }

        return false;
    }

    /**
     * Checks to see if the specified value is a member of the set of valid
     * values.
     *
     * @param value
     *            The value to be checked.
     *
     * @return {@link ValueConstraint#VALID} if the value is a member of the
     *         valid values, otherwise it returns a string indicated that value
     *         is not a member of the valid set of values.
     */
    public String getValueCompliance(Object value) {
        if (this.validValues.contains(value)) {
            return ValueConstraint.VALID;
        }
        return "The specified value, '" + value
                + "' is not a member of the set of valid values: '"
                + this.validValues + "'";
    }
}
