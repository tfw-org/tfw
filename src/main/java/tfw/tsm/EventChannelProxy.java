package tfw.tsm;

import tfw.check.Argument;
import tfw.tsm.ecd.EventChannelDescription;

public final class EventChannelProxy implements Proxy
{
	private final EventChannel eventChannel;
	
	EventChannelProxy(EventChannel eventChannel)
	{
		Argument.assertNotNull(eventChannel, "eventChannel");
		
		this.eventChannel = eventChannel;
	}
	
	public String getName()
	{
		return(eventChannel.getECD().getEventChannelName());
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