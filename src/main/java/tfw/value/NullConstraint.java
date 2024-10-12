package tfw.value;

import tfw.check.Argument;

/**
 * A constaint where the only legal value is <code>null</code>.
 */
public class NullConstraint extends ValueConstraint<Object> {
    public static final NullConstraint INSTANCE = new NullConstraint();

    private NullConstraint() {}

    /* (non-Javadoc)
     * @see co2.value.Constraint#isCompatable(co2.value.Constraint)
     */
    @Override
    public boolean isCompatible(ValueConstraint<?> constraint) {
        Argument.assertNotNull(constraint, "constraint");

        return constraint instanceof NullConstraint;
    }

    /* (non-Javadoc)
     * @see co2.value.Constraint#getValueCompliance(java.lang.Object)
     */
    @Override
    public String getValueCompliance(Object value) {
        if (value == null) {
            return VALID;
        } else {
            return "Trigger event channels have no values, so no value complies with this constraint";
        }
    }
}
