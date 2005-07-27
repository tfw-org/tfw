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

import java.util.Iterator;
import java.util.Map;

import tfw.check.Argument;

public final class BranchProxy
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
		EventChannelProxy[] eventChannelProxies =
			new EventChannelProxy[branch.eventChannels.size()];
		Iterator iterator = branch.eventChannels.values().iterator();

		for (int i=0 ; iterator.hasNext() ; i++)
		{
			eventChannelProxies[i] =
				new EventChannelProxy((EventChannel)iterator.next());
		}
		
		return(eventChannelProxies);
	}
	
	public Object[] getChildProxies()
	{
		Map children = branch.getChildren();
		
		Object[] o = new Object[children.size()];
		Iterator iterator = children.values().iterator();
		
		for (int i=0 ; i < o.length ; i++)
		{
			TreeComponent treeComponent = (TreeComponent)iterator.next();
			
			if (treeComponent instanceof Root)
			{
				o[i] = new RootProxy((Root)treeComponent);
			}
			else if (treeComponent instanceof Branch)
			{
				o[i] = new BranchProxy((Branch)treeComponent);
			}
			else if (treeComponent instanceof Commit)
			{
				o[i] = new CommitProxy((Commit)treeComponent);
			}
			else if (treeComponent instanceof Converter)
			{
				o[i] = new ConverterProxy((Converter)treeComponent);
			}
			else if (treeComponent instanceof Initiator)
			{
				o[i] = new InitiatorProxy((Initiator)treeComponent);
			}
			else if (treeComponent instanceof MultiplexedBranch)
			{
				o[i] = new MultiplexedBranchProxy(
					(MultiplexedBranch)treeComponent);
			}
			else if (treeComponent instanceof Synchronizer)
			{
				o[i] = new SynchronizerProxy((Synchronizer)treeComponent);
			}
			else if (treeComponent instanceof TriggeredCommit)
			{
				o[i] = new TriggeredCommitProxy((TriggeredCommit)treeComponent);
			}
			else if (treeComponent instanceof TriggeredConverter)
			{
				o[i] = new TriggeredConverterProxy(
					(TriggeredConverter)treeComponent);
			}
			else if (treeComponent instanceof Validator)
			{
				o[i] = new ValidatorProxy((Validator)treeComponent);
			}
		}
		
		return(o);
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