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

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import tfw.check.Argument;

public final class TriggeredConverterProxy implements Proxy
{
	private final TriggeredConverter triggeredConverter;
	
	public TriggeredConverterProxy(TriggeredConverter triggeredConverter)
	{
		Argument.assertNotNull(triggeredConverter, "triggeredConverter");
		
		this.triggeredConverter = triggeredConverter;
	}
	
	public String getName()
	{
		return(triggeredConverter.getName());
	}
	
	public SourceProxy[] getSourceProxies()
	{
		Collection collection = new TreeMap(triggeredConverter.sources).values();
		Iterator iterator = collection.iterator();
		SourceProxy[] sp = new SourceProxy[collection.size()];
		
		for (int i=0 ; iterator.hasNext() ; i++)
		{
			Object o = iterator.next();
			sp[i] = new SourceProxy((Source)o);
		}
		return(sp);
	}
	
	public SinkProxy[] getSinkProxies()
	{
		Object[] sinks = (Object[])triggeredConverter.sinks.values().toArray();
		SinkProxy[] sp = new SinkProxy[sinks.length];
		
		for (int i=0 ; i < sinks.length ; i++)
		{
			sp[i] = new SinkProxy((Sink)sinks[i]);
		}
		return(sp);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof TriggeredConverterProxy)
		{
			TriggeredConverterProxy ip = (TriggeredConverterProxy)obj;
			
			return(triggeredConverter.equals(ip.triggeredConverter));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(triggeredConverter.hashCode());
	}
}