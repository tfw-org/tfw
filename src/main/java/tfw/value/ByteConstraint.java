package tfw.value;

/**
 * An byte value constraint.
 */
public class ByteConstraint extends RangeConstraint<Byte> {
    /**
     * Creates a constraint with the specified attributes.
     * @param min the minimum legal value.
     * @param max the maximum legal value.
     */
    public ByteConstraint(byte min, byte max) {
        super(Byte.class, Byte.valueOf(min), Byte.valueOf(max), true, true);
    }

    @Override
    protected int compareTo(Byte left, Byte right) {
        return left.compareTo(right);
    }
}
