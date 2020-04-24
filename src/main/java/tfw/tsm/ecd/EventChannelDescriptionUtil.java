package tfw.tsm.ecd;

import	java.util.HashSet;
import	tfw.tsm.ecd.EventChannelDescription;

public class EventChannelDescriptionUtil
{
	private EventChannelDescriptionUtil() {}
	
	public static EventChannelDescription[] create(
		EventChannelDescription[] ecd)
	{
		if (ecd == null)
			throw new IllegalArgumentException(
				"ecd == null not allowed!");
		
		HashSet set = new HashSet();
		for (int i=0 ; i < ecd.length ; i++)
		{
			if (ecd[i] != null)
			{				
				set.add(ecd[i]);
			}
		}
				
		return((EventChannelDescription[])set.toArray(
			new EventChannelDescription[set.size()]));
	}
}
