package tfw.value;

/**
 * An integer value constraint.
 */
public class LongConstraint extends RangeConstraint {
	/**
	 * Creates a constraint with the specified attributes.
	 * @param min the minimum legal value.
	 * @param max the maximum legal value.
	 */
	public LongConstraint(long min, long max){
		super(Integer.class, new Long(min), new Long(max), true, true);
	}
}
