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
			TreeComponent treeComponent = iterator.next();
			
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
	
	public Proxy getParentProxy()
	{
		TreeComponent branchParent = branch.immediateParent;
		
		if (branchParent == null)
		{
			return(null);
		}
		else if (branchParent instanceof Root)
		{
			return(new RootProxy((Root)branchParent));
		}
		else if (branchParent instanceof MultiplexedBranch)
		{
			return(new MultiplexedBranchProxy((MultiplexedBranch)branchParent));
		}
		else if (branchParent instanceof Branch)
		{
			return(new BranchProxy((Branch)branchParent));
		}
		
		throw new IllegalStateException(
			"Parent is not a branch/multiplexedBranch");
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