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

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;

public final class EventChannelProxy
{
	private final EventChannel eventChannel;
	
	EventChannelProxy(EventChannel eventChannel)
	{
		Argument.assertNotNull(eventChannel, "eventChannel");
		
		this.eventChannel = eventChannel;
	}
	
	public EventChannelDescription getEventChannelDescription()
	{
		return(eventChannel.getECD());
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof EventChannelProxy)
		{
			EventChannelProxy tp = (EventChannelProxy)obj;
			
			return(eventChannel.equals(tp.eventChannel));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(eventChannel.hashCode());
	}
}