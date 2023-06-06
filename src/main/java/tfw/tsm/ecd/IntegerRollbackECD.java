package tfw.tsm.ecd;

import tfw.value.ClassValueConstraint;

/**
 * A <code>java.lang.String</code> rollback event channel description
 */
public class IntegerRollbackECD extends RollbackECD {
	/**
	 * Creates an event channel description with the specified name.
	 * @param name the name of the event channel.
	 */
	public IntegerRollbackECD(String name)
	{
		super(name, ClassValueConstraint.INTEGER);
	}
}
