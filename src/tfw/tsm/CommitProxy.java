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

public final class CommitProxy implements Proxy
{
	private final Commit commit;
	
	public CommitProxy(Commit commit)
	{
		Argument.assertNotNull(commit, "commit");
		
		this.commit = commit;
	}
	
	public String getName()
	{
		return(commit.getName());
	}
	
	public SinkProxy[] getSinkProxies()
	{
		Object[] sinks = (Object[])commit.sinks.values().toArray();
		SinkProxy[] sp = new SinkProxy[sinks.length];
		
		for (int i=0 ; i < sinks.length ; i++)
		{
			sp[i] = new SinkProxy((Sink)sinks[i]);
		}
		return(sp);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof CommitProxy)
		{
			CommitProxy ip = (CommitProxy)obj;
			
			return(commit.equals(ip.commit));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(commit.hashCode());
	}
}