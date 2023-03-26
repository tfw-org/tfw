package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;

/**
 * A <code>java.lang.String</code> rollback event channel description
 */
public class StringRollbackECD extends RollbackECD {
	/**
	 * Creates an event channel description with the specified name.
	 * @param name the name of the event channel.
	 */
	public StringRollbackECD(String name)
	{
		super(name, ClassValueConstraint.STRING);
	}
}
