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

public final class SinkProxy implements Proxy
{
	private final Sink sink;
	
	SinkProxy(Sink sink)
	{
		Argument.assertNotNull(sink, "sink");
		
		this.sink = sink;
	}
	
	public EventChannelProxy getEventChannelProxy()
	{
		return(new EventChannelProxy((Terminator)sink.getEventChannel()));
	}
	
	public String getName()
	{
		return(sink.getEventChannelName());
	}
	
	public boolean isTriggering()
	{
		return(sink.isTriggering());
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof SinkProxy)
		{
			SinkProxy sp = (SinkProxy)obj;
			
			return(sink.equals(sp.sink));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(sink.hashCode());
	}
}