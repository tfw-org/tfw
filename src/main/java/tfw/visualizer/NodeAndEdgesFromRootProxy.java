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
package tfw.visualizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SinkProxy;
import tfw.tsm.SourceProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;

public final class NodeAndEdgesFromRootProxy
{
	private ObjectIla nodesObjectIla = null;
	private ObjectIla edgeFromsObjectIla = null;
	private ObjectIla edgeTosObjectIla = null;
	
	public ObjectIla getNodesObjectIla() {
		if (nodesObjectIla == null) {
			calculateArrays();
			nodesObjectIla = ObjectIlaFromArray.create(nodes, false);
		}
		return nodesObjectIla;
	}

	public ObjectIla getEdgeFromsObjectIla() {
		if (edgeFromsObjectIla == null) {
			calculateArrays();
			edgeFromsObjectIla = ObjectIlaFromArray.create(edgeFroms, false);
		}
		return edgeFromsObjectIla;
	}

	public ObjectIla getEdgeTosObjectIla() {
		if (edgeTosObjectIla == null) {
			calculateArrays();
			edgeTosObjectIla = ObjectIlaFromArray.create(edgeTos, false);
		}
		return edgeTosObjectIla;
	}

	private final RootProxy rootProxy;
	
	private Object[] nodes = null;
	private Object[] edgeFroms = null;
	private Object[] edgeTos = null;
	
	public NodeAndEdgesFromRootProxy(RootProxy rootProxy)
	{
		this.rootProxy = rootProxy;
	}

	private void calculateArrays()
	{
		if (nodes != null)
		{
			return;
		}
		
		HashSet nodesList = new HashSet();
		ArrayList edgeFromsList = new ArrayList();
		ArrayList edgeTosList = new ArrayList();
		
		addStructuralNode(nodesList, edgeFromsList, edgeTosList, rootProxy, null);
		
		nodes = nodesList.toArray();;
		edgeFroms = edgeFromsList.toArray();
		edgeTos = edgeTosList.toArray();
	}
	
	private static void addStructuralNode(Set nodes, ArrayList edgeFroms,
			ArrayList edgeTos, Object proxy, Object parent)
	{
		nodes.add(proxy);
		
		if (parent != null)
		{
			edgeFroms.add(parent);
			edgeTos.add(proxy);
		}
		
		if (proxy instanceof RootProxy)
		{
			RootProxy rootProxy = (RootProxy)proxy;
			Object[] childProxies = rootProxy.getChildProxies();
			EventChannelProxy[] eventChannelProxies = rootProxy.getEventChannelProxies();
			
			Arrays.sort(childProxies, ProxyNameComparator.INSTANCE);
			Arrays.sort(eventChannelProxies, ProxyNameComparator.INSTANCE);
			
			for (int i=0 ; i < eventChannelProxies.length ; i++)
			{
				nodes.add(eventChannelProxies[i]);
				edgeFroms.add(proxy);
				edgeTos.add(eventChannelProxies[i]);
			}
			for (int i=0 ; i < childProxies.length ; i++)
			{
				addStructuralNode(nodes, edgeFroms, edgeTos,
					childProxies[i], proxy);
			}
		}
		else if (proxy instanceof BranchProxy)
		{
			BranchProxy branchProxy = (BranchProxy)proxy;
			Object[] childProxies = (branchProxy).getChildProxies();
			EventChannelProxy[] eventChannelProxies = branchProxy.getEventChannelProxies();
			
			Arrays.sort(childProxies, ProxyNameComparator.INSTANCE);
			Arrays.sort(eventChannelProxies, ProxyNameComparator.INSTANCE);
			
			for (int i=0 ; i < eventChannelProxies.length ; i++)
			{
				nodes.add(eventChannelProxies[i]);
				edgeFroms.add(proxy);
				edgeTos.add(eventChannelProxies[i]);
			}
			for (int i=0 ; i < childProxies.length ; i++)
			{
				addStructuralNode(nodes, edgeFroms, edgeTos,
					childProxies[i], proxy);
			}
		}
		else if (proxy instanceof CommitProxy)
		{
			CommitProxy commitProxy = (CommitProxy)proxy;
			
			addSinkEdges(commitProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof ConverterProxy)
		{
			ConverterProxy converterProxy = (ConverterProxy)proxy;
			
			addSourceEdges(converterProxy.getSourceProxies(),
				proxy, edgeFroms, edgeTos);
			addSinkEdges(converterProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof InitiatorProxy)
		{
			InitiatorProxy initiatorProxy = (InitiatorProxy)proxy;
			
			addSourceEdges(initiatorProxy.getSourceProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof SynchronizerProxy)
		{
			SynchronizerProxy synchronizerProxy = (SynchronizerProxy)proxy;
			
			addSourceEdges(synchronizerProxy.getSourceProxies(),
				proxy, edgeFroms, edgeTos);
			addSinkEdges(synchronizerProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof TriggeredCommitProxy)
		{
			TriggeredCommitProxy triggeredCommitProxy = (TriggeredCommitProxy)proxy;
			
			addSinkEdges(triggeredCommitProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof TriggeredConverterProxy)
		{
			TriggeredConverterProxy triggeredConverterProxy = (TriggeredConverterProxy)proxy;
			
			addSourceEdges(triggeredConverterProxy.getSourceProxies(),
				proxy, edgeFroms, edgeTos);
			addSinkEdges(triggeredConverterProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
		else if (proxy instanceof ValidatorProxy)
		{
			ValidatorProxy validatorProxy = (ValidatorProxy)proxy;
			
			addSinkEdges(validatorProxy.getSinkProxies(),
				proxy, edgeFroms, edgeTos);
		}
	}
	
	private static void addSourceEdges(SourceProxy[] sourceProxies,
		Object proxy, ArrayList edgeFroms, ArrayList edgeTos)
	{
		Arrays.sort(sourceProxies, ProxyNameComparator.INSTANCE);
		
		for (int i=0 ; i < sourceProxies.length ; i++)
		{
			edgeFroms.add(proxy);
			edgeTos.add(sourceProxies[i].getEventChannelProxy());
		}
	}
	
	private static void addSinkEdges(SinkProxy[] sinkProxies,
		Object proxy, ArrayList edgeFroms, ArrayList edgeTos)
	{
		Arrays.sort(sinkProxies, ProxyNameComparator.INSTANCE);
		
		for (int i=0 ; i < sinkProxies.length ; i++)
		{
			edgeFroms.add(sinkProxies[i].getEventChannelProxy());
			edgeTos.add(proxy);
		}
	}
}