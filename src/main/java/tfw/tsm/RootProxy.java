package tfw.tsm;

import java.util.Iterator;
import java.util.Map;

import tfw.check.Argument;

public final class RootProxy implements Proxy
{
	private final Root root;
	
	public RootProxy(Root root)
	{
		Argument.assertNotNull(root, "root");
		
		this.root = root;
	}
	
	public String getName()
	{
		return(root.getName());
	}
	
	public EventChannelProxy[] getEventChannelProxies()
	{
		EventChannelProxy[] eventChannelProxies =
			new EventChannelProxy[root.eventChannels.size()];
		Iterator<EventChannel> iterator = root.eventChannels.values().iterator();

		for (int i=0 ; iterator.hasNext() ; i++)
		{
			eventChannelProxies[i] =
				new EventChannelProxy((EventChannel)iterator.next());
		}
		
		return(eventChannelProxies);
	}
	
	public Proxy[] getChildProxies()
	{
		Map<String, TreeComponent> children = root.getChildren();
		
		Proxy[] proxies = new Proxy[children.size()];
		Iterator<TreeComponent> iterator = children.values().iterator();
		
		for (int i=0 ; i < proxies.length ; i++)
		{
			Object treeComponent = iterator.next();
			
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
		if (obj instanceof RootProxy)
		{
			RootProxy rp = (RootProxy)obj;
			
			return(root.equals(rp.root));
		}
		
		return(false);
	}
	
	public int hashCode()
	{
		return(root.hashCode());
	}
}