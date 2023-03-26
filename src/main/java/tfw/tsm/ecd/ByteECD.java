package tfw.tsm.ecd;

import tfw.value.ByteConstraint;
import tfw.value.ClassValueConstraint;

/**
 * A <code>java.lang.Integer</code> event channel descritpion
 */
public class ByteECD extends ObjectECD {
	/**
	 * Creates an event channel description with the specified attribute.
	 * @param name the name of the event channel.
	 * @param min the minimum value allowed.
	 * @param max the maximum value allowed.
	 */
	public ByteECD(String name, byte min, byte max)
	{
		super(name, new ByteConstraint(min, max));
	}

	/**
	 * Creates an event channel description with the specified name.
	 * @param name the name of the event channel.
	 */
	public ByteECD(String name)
	{
		super(name, ClassValueConstraint.BYTE);
	}

}
