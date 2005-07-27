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
package tfw.visualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.Root;
import tfw.tsm.RootProxy;
import tfw.tsm.SinkProxy;
import tfw.tsm.SourceProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.LongIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class NodeEdgeConverter extends TriggeredConverter
{
	private final Root root;
	private final ObjectIlaECD nodesECD;
	private final LongIlaECD edgeFromsECD;
	private final LongIlaECD edgeTosECD;
	
	public NodeEdgeConverter(Root root, StatelessTriggerECD triggerECD,
		ObjectIlaECD nodesECD, LongIlaECD edgeFromsECD,
		LongIlaECD edgeTosECD)
	{
		super("NodeEdgeConverter",
			triggerECD,
			null,
			new EventChannelDescription[] {nodesECD, edgeFromsECD, edgeTosECD});
		
		this.root = root;
		this.nodesECD = nodesECD;
		this.edgeFromsECD = edgeFromsECD;
		this.edgeTosECD = edgeTosECD;
	}
	
	protected void convert()
	{		
		HashMap nodes = new HashMap();
		ArrayList edgeFroms = new ArrayList();
		ArrayList edgeTos = new ArrayList();
		
		addStructuralNode(nodes, edgeFroms, edgeTos,
			new RootProxy(root), new Long(0));
		
		Object[] nodesArray = new Object[nodes.size()];
		for (Iterator i = nodes.keySet().iterator() ; i.hasNext() ; )
		{
			Object proxy = i.next();
			int index = ((Long)nodes.get(proxy)).intValue();
			
			nodesArray[index] = proxy;
		}
		set(nodesECD, ObjectIlaFromArray.create(nodesArray));
		
		long[] edgeFromsArray = new long[edgeFroms.size()];
		long[] edgeTosArray = new long[edgeTos.size()];
		
		for (int i=0 ; i < edgeFromsArray.length ; i++)
		{
			edgeFromsArray[i] = ((Long)edgeFroms.get(i)).longValue();
			edgeTosArray[i] = ((Long)edgeTos.get(i)).longValue();
		}
		
		set(edgeFromsECD, LongIlaFromArray.create(edgeFromsArray));
		set(edgeTosECD, LongIlaFromArray.create(edgeTosArray));
	}
	
	private static void addStructuralNode(HashMap nodes, ArrayList edgeFroms,
		ArrayList edgeTos, Object proxy, Long parent)
	{
		Long index = getAndCreateNode(nodes, proxy);

		if (index.longValue() != 0)
		{
			edgeFroms.add(parent);
			edgeTos.add(index);
		}
		
		Object[] childProxies = null;
		EventChannelProxy[] eventChannelProxies = null;
		
		if (proxy instanceof RootProxy)
		{
			RootProxy rootProxy = (RootProxy)proxy;
			
			childProxies = rootProxy.getChildProxies();
			eventChannelProxies = rootProxy.getEventChannelProxies();
			
			for (int i=0 ; i < childProxies.length ; i++)
			{
				addStructuralNode(nodes, edgeFroms, edgeTos,
					childProxies[i], index);
			}
			for (int i=0 ; i < eventChannelProxies.length ; i++)
			{
				Long ecpIndex = getAndCreateNode(nodes, eventChannelProxies[i]);
				
				edgeFroms.add(parent);
				edgeTos.add(ecpIndex);
			}
		}
		else if (proxy instanceof BranchProxy)
		{
			BranchProxy branchProxy = (BranchProxy)proxy;
			
			childProxies = (branchProxy).getChildProxies();
			eventChannelProxies = branchProxy.getEventChannelProxies();
			
			for (int i=0 ; i < childProxies.length ; i++)
			{
				addStructuralNode(nodes, edgeFroms, edgeTos,
					childProxies[i], index);
			}
			for (int i=0 ; i < eventChannelProxies.length ; i++)
			{
				Long ecpIndex = getAndCreateNode(nodes, eventChannelProxies[i]);
				
				edgeFroms.add(parent);
				edgeTos.add(ecpIndex);
			}
		}
		else if (proxy instanceof CommitProxy)
		{
			CommitProxy commitProxy = (CommitProxy)proxy;
			
			addSinkEdges(commitProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof ConverterProxy)
		{
			ConverterProxy converterProxy = (ConverterProxy)proxy;
			
			addSourceEdges(converterProxy.getSourceProxies(),
				index, nodes, edgeFroms, edgeTos);
			addSinkEdges(converterProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof InitiatorProxy)
		{
			InitiatorProxy initiatorProxy = (InitiatorProxy)proxy;
			
			addSourceEdges(initiatorProxy.getSourceProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof SynchronizerProxy)
		{
			SynchronizerProxy synchronizerProxy = (SynchronizerProxy)proxy;
			
			addSourceEdges(synchronizerProxy.getSourceProxies(),
				index, nodes, edgeFroms, edgeTos);
			addSinkEdges(synchronizerProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof TriggeredCommitProxy)
		{
			TriggeredCommitProxy triggeredCommitProxy = (TriggeredCommitProxy)proxy;
			
			addSinkEdges(triggeredCommitProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof TriggeredConverterProxy)
		{
			TriggeredConverterProxy triggeredConverterProxy = (TriggeredConverterProxy)proxy;
			
			addSourceEdges(triggeredConverterProxy.getSourceProxies(),
				index, nodes, edgeFroms, edgeTos);
			addSinkEdges(triggeredConverterProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
		else if (proxy instanceof ValidatorProxy)
		{
			ValidatorProxy validatorProxy = (ValidatorProxy)proxy;
			
			addSinkEdges(validatorProxy.getSinkProxies(),
				index, nodes, edgeFroms, edgeTos);
		}
	}
	
	private static Long getAndCreateNode(Map map, Object object)
	{
		Long l = (Long)map.get(object);
		
		if (l == null)
		{
			l = new Long(map.size());
			map.put(object, l);
		}
		
		return(l);
	}
	
	private static void addSourceEdges(SourceProxy[] sourceProxies,
		Long leafIndex, Map nodes, ArrayList edgeFroms, ArrayList edgeTos)
	{
		for (int i=0 ; i < sourceProxies.length ; i++)
		{
			EventChannelProxy eventChannelProxy = sourceProxies[i].getEventChannelProxy();
			
			Long ecpIndex = getAndCreateNode(nodes, eventChannelProxy);
			
			edgeFroms.add(leafIndex);
			edgeTos.add(ecpIndex);
		}
	}
	
	private static void addSinkEdges(SinkProxy[] sinkProxies,
		Long leafIndex, Map nodes, ArrayList edgeFroms, ArrayList edgeTos)
	{
		for (int i=0 ; i < sinkProxies.length ; i++)
		{
			EventChannelProxy eventChannelProxy = sinkProxies[i].getEventChannelProxy();
			
			Long ecpIndex = getAndCreateNode(nodes, eventChannelProxy);
			
			edgeFroms.add(ecpIndex);
			edgeTos.add(leafIndex);
		}
	}
}