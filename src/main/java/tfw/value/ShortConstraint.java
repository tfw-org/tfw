package tfw.value;

/**
 * An short value constraint.
 */
public class ShortConstraint extends RangeConstraint {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public ShortConstraint(short min, short max) {
        super(Short.class, new Short(min), new Short(max), true, true);
    }
}
