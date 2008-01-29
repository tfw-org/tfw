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
 * without even the implied warranty of
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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import tfw.check.Argument;

public final class BranchProxy implements Proxy
{
	private final Branch branch;
	
	public BranchProxy(Branch branch)
	{
		Argument.assertNotNull(branch, "branch");
		this.branch = branch;
	}
	
	public String getName()
	{
		return(branch.getName());
	}
	
	public EventChannelProxy[] getEventChannelProxies()
	{
		Object[] eventChannels = branch.eventChannels.values().toArray();
		EventChannelProxy[] eventChannelProxies =
			new EventChannelProxy[eventChannels.length];
		
		for (int i=0 ; i < eventChannels.length ; i++)
		{
			eventChannelProxies[i] = 
				new EventChannelProxy((EventChannel)eventChannels[i]);
		}
		
		return(eventChannelProxies);
	}
	
	public Proxy[] getChildProxies()
	{
		Map<String, TreeComponent> children =
			new TreeMap<String, TreeComponent>(branch.getChildren());
		
		Proxy[] proxies = new Proxy[children.size()];
		Iterator<TreeComponent> iterator = children.values().iterator();
		
		for (int i=0 ; i < proxies.length ; i++)
		{
			TreeComponent treeComponent = (TreeComponent)iterator.next();
			
			if (treeComponent instanceof Root)
			{
				proxies[i] = new RootProxy((Root)treeComponent);
			}
			else if (treeComponent instanceof Branch)
			{
				proxies[i] = new BranchProxy((Branch)treeComponent);
			}
			else if (treeComponent instanceof Commit)
			{
				proxies[i] = new CommitProxy((Commit)treeComponent);
			}
			else if (treeComponent instanceof Converter)
			{
				proxies[i] = new ConverterProxy((Converter)treeComponent);
			}
			else if (treeComponent instanceof Initiator)
			{
				proxies[i] = new InitiatorProxy((Initiator)treeComponent);
			}
			else if (treeComponent instanceof MultiplexedBranch)
			{
				proxies[i] = new MultiplexedBranchProxy(
					(MultiplexedBranch)treeComponent);
			}
			else if (treeComponent instanceof Synchronizer)
			{
				proxies[i] = new SynchronizerProxy((Synchronizer)treeComponent);
			}
			else if (treeComponent instanceof TriggeredCommit)
			{
				proxies[i] = new TriggeredCommitProxy((TriggeredCommit)treeComponent);
			}
			else if (treeComponent instanceof TriggeredConverter)
			{
				proxies[i] = new TriggeredConverterProxy(
					(TriggeredConverter)treeComponent);
			}
			else if (treeComponent instanceof Validator)
			{
				proxies[i] = new ValidatorProxy((Validator)treeComponent);
			}
		}
		
		return(proxies);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof BranchProxy)
		{
			BranchProxy bp = (BranchProxy)obj;
			
			return(branch.equals(bp.branch));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(branch.hashCode());
	}
}