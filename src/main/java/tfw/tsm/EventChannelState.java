package tfw.tsm;

import java.io.IOException;
import java.io.Serializable;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.value.ValueException;

/**
 * A container for event channel state.
 */
public class EventChannelState implements Serializable
{
	private static final long serialVersionUID = -8852353620619649029L;

	private final String eventChannelName;

	private final Object state;

	/**
	 * Creates an event channel state with the specified values
	 * 
	 * @param ecd
	 *            the description of the event channel.
	 * @param state
	 *            the state of the event channel.
	 * @throws ValueException
	 *             if the specified value is not compatible with the constraint
	 *             defined in the specified event channel description.
	 */
	public EventChannelState(EventChannelDescription ecd, Object state)
			throws ValueException
	{
		Argument.assertNotNull(ecd, "ecd");
		ecd.getConstraint().checkValue(state);
		this.state = state;
		this.eventChannelName = ecd.getEventChannelName();
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		in.defaultReadObject();
		if (this.eventChannelName == null)
		{
			throw new IllegalStateException(
					"De-serialization failed, eventChannelName == null not allowed.");
		}

		if (this.eventChannelName.length() == 0)
		{
			throw new IllegalStateException(
					"De-serialization failed, eventChannelName.lenth() == 0 not allowed");
		}
	}

	/**
	 * Returns the event channel description.
	 * 
	 * @return the event channel description.
	 */
	public String getEventChannelName()
	{
		return this.eventChannelName;
	}

	/**
	 * Returns the event channel state.
	 * 
	 * @return the event channel state.
	 */
	public Object getState()
	{
		return this.state;
	}

	/**
	 * Returns a hash code value for this event channel state.
	 * 
	 * @return a hash code value for this event channel state.
	 */
	public int hashCode()
	{
		return state.hashCode() + eventChannelName.hashCode();
	}

	/**
	 * Returns <code>true</code> if the object is and instance of
	 * <code>EventChannelState</code> and each of its attributes are equal to
	 * the attributes of this object.
	 * 
	 * @return <code>true</code> if the object is and instance of
	 *         <code>EventChannelState</code> and each of its attributes are
	 *         equal to the attributes of this object.
	 */
	public boolean equals(Object object)
	{
		if (!(object instanceof EventChannelState))
		{
			return false;
		}
		EventChannelState ecs = (EventChannelState) object;

		return eventChannelName.equals(ecs.eventChannelName)
				&& state.equals(ecs.state);
	}

	/**
	 * Returns a string representation of this event channel state.
	 * 
	 * @return a string representation of this event channel state.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("EventChannelState[");
		sb.append(eventChannelName).append(" = ").append(state);
		sb.append("]");
		return sb.toString();
	}
}
