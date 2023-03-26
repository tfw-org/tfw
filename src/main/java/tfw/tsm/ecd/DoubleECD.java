package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;
import tfw.value.DoubleConstraint;

/**
 * A <code>java.lang.Double</code> event channel descritpion.
 */
public class DoubleECD extends ObjectECD {
	
	/**
	 * Creates an event channel description with the specified attribute.
	 * @param name the name of the event channel.
	 * @param min the minimum value allowed.
	 * @param max the maximum value allowed.
     * @param minInclusive if true <code>min</code> is a valid value,
     * otherwise it is not valid.
     * @param maxInclusive if true <code>max</code> is a valid value,
     * otherwise it is not valid.
	 */
	public DoubleECD(String name, double min, double max, boolean minInclusive, boolean maxInclusive)
	{
		super(name, new DoubleConstraint(min, max, minInclusive, maxInclusive));
	}
	
	/**
	 * Creates an event channel description with the specified name.
	 * @param name the name of the event channel.
	 */
	public DoubleECD(String name){
		super(name, ClassValueConstraint.DOUBLE);
	}

}
