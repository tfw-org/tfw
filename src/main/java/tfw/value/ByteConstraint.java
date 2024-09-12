package tfw.value;

/**
 * An byte value constraint.
 */
public class ByteConstraint extends RangeConstraint {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public ByteConstraint(byte min, byte max) {
        super(Byte.class, new Byte(min), new Byte(max), true, true);
    }
}
