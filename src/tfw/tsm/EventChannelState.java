/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm;

import java.io.Serializable;

import tfw.check.Argument;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ValueException;

/**
 * A container for event channel state.
 */
public class EventChannelState implements Serializable
{
    private static final long serialVersionUID = -8848929104594047886L;
    
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
	public EventChannelState(ObjectECD ecd, Object state) throws ValueException
	{
		Argument.assertNotNull(ecd, "ecd");
		ecd.getConstraint().checkValue(state);
		this.state = state;
		this.eventChannelName = ecd.getEventChannelName();
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
