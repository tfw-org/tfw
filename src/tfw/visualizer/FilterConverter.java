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

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BranchProxy;
import tfw.tsm.CommitProxy;
import tfw.tsm.Converter;
import tfw.tsm.ConverterProxy;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.InitiatorProxy;
import tfw.tsm.MultiplexedBranchProxy;
import tfw.tsm.RootProxy;
import tfw.tsm.SynchronizerProxy;
import tfw.tsm.TriggeredCommitProxy;
import tfw.tsm.TriggeredConverterProxy;
import tfw.tsm.ValidatorProxy;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ila.LongIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class FilterConverter extends Converter
{
	private final ObjectIlaECD nodesECD;
	private final LongIlaECD edgeFromsECD;
	private final LongIlaECD edgeTosECD;
	private final BooleanECD showBranchesECD;
	private final BooleanECD showCommitsECD;
	private final BooleanECD showConvertersECD;
	private final BooleanECD showEventChannelsECD;
	private final BooleanECD showInitiatorsECD;
	private final BooleanECD showMultiplexedBranchesECD;
	private final BooleanECD showRootsECD;
	private final BooleanECD showSynchronizersECD;
	private final BooleanECD showTriggeredCommitsECD;
	private final BooleanECD showTriggeredConvertersECD;
	private final BooleanECD showValidatorsECD;
	private final ObjectIlaECD filteredNodesECD;
	private final LongIlaECD filteredEdgeFromsECD;
	private final LongIlaECD filteredEdgeTosECD;
	
	public FilterConverter(ObjectIlaECD nodesECD, LongIlaECD edgeFromsECD,
		LongIlaECD edgeTosECD, BooleanECD showBranchesECD,
		BooleanECD showCommitsECD, BooleanECD showConvertersECD,
		BooleanECD showEventChannelsECD, BooleanECD showInitiatorsECD,
		BooleanECD showMultiplexedBranchesECD, BooleanECD showRootsECD,
		BooleanECD showSynchronizersECD, BooleanECD showTriggeredCommitsECD,
		BooleanECD showTriggeredConvertersECD, BooleanECD showValidatorsECD,
		ObjectIlaECD filteredNodesECD, LongIlaECD filteredEdgeFromsECD,
		LongIlaECD filteredEdgeTosECD)
	{
		super("FilterConverter",
			new EventChannelDescription[] {nodesECD, edgeFromsECD, edgeTosECD,
				showBranchesECD, showCommitsECD, showConvertersECD,
				showEventChannelsECD, showInitiatorsECD,
				showMultiplexedBranchesECD, showRootsECD, showSynchronizersECD,
				showTriggeredCommitsECD, showTriggeredConvertersECD,
				showValidatorsECD},
			null,
			new EventChannelDescription[] {filteredNodesECD,
				filteredEdgeFromsECD, filteredEdgeTosECD});
		
		this.nodesECD = nodesECD;
		this.edgeFromsECD = edgeFromsECD;
		this.edgeTosECD = edgeTosECD;
		this.showBranchesECD = showBranchesECD;
		this.showCommitsECD = showCommitsECD;
		this.showConvertersECD = showConvertersECD;
		this.showEventChannelsECD = showEventChannelsECD;
		this.showInitiatorsECD = showInitiatorsECD;
		this.showMultiplexedBranchesECD = showMultiplexedBranchesECD;
		this.showRootsECD = showRootsECD;
		this.showSynchronizersECD = showSynchronizersECD;
		this.showTriggeredCommitsECD = showTriggeredCommitsECD;
		this.showTriggeredConvertersECD = showTriggeredConvertersECD;
		this.showValidatorsECD = showValidatorsECD;
		this.filteredNodesECD = filteredNodesECD;
		this.filteredEdgeFromsECD = filteredEdgeFromsECD;
		this.filteredEdgeTosECD = filteredEdgeTosECD;
	}
	
	protected void convert()
	{
		Object[] nodes = null;
		long[] edgeFroms = null;
		long[] edgeTos = null;
		boolean showBranches =
			((Boolean)get(showBranchesECD)).booleanValue();
		boolean showCommits =
			((Boolean)get(showCommitsECD)).booleanValue();
		boolean showConverters =
			((Boolean)get(showConvertersECD)).booleanValue();
		boolean showEventChannels =
			((Boolean)get(showEventChannelsECD)).booleanValue();
		boolean showInitiators =
			((Boolean)get(showInitiatorsECD)).booleanValue();
		boolean showMultiplexedBranches =
			((Boolean)get(showMultiplexedBranchesECD)).booleanValue();
		boolean showRoots =
			((Boolean)get(showRootsECD)).booleanValue();
		boolean showSynchronizers =
			((Boolean)get(showSynchronizersECD)).booleanValue();
		boolean showTriggeredCommits =
			((Boolean)get(showTriggeredCommitsECD)).booleanValue();
		boolean showTriggeredConverters =
			((Boolean)get(showTriggeredConvertersECD)).booleanValue();
		boolean showValidators =
			((Boolean)get(showValidatorsECD)).booleanValue();
		HashMap nodeMap = new HashMap();
		
		try
		{
			nodes = ((ObjectIla)get(nodesECD)).toArray();
			edgeFroms = ((LongIla)get(edgeFromsECD)).toArray();
			edgeTos = ((LongIla)get(edgeTosECD)).toArray();
		}
		catch (DataInvalidException e)
		{
			return;
		}
		
		for (int i=0 ; i < nodes.length ; i++)
		{
			Object node = nodes[i];
			
			if (node instanceof BranchProxy && showBranches)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof CommitProxy && showCommits)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof ConverterProxy && showConverters)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof EventChannelProxy && showEventChannels)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof InitiatorProxy && showInitiators)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof MultiplexedBranchProxy && showMultiplexedBranches)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof RootProxy && showRoots)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof SynchronizerProxy && showSynchronizers)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof TriggeredCommitProxy && showTriggeredCommits)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof TriggeredConverterProxy && showTriggeredConverters)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
			else if (node instanceof ValidatorProxy && showValidators)
			{
				nodeMap.put(new Long(i), new Long(nodeMap.size()));
			}
		}
		
		Object[] filteredNodesArray = new Object[nodeMap.size()];
		Iterator iterator = nodeMap.keySet().iterator();
		
		for (int i=0 ; iterator.hasNext() ; i++)
		{
			Long oldNodeIndex = (Long)iterator.next();
			long newNodeIndex = ((Long)nodeMap.get(oldNodeIndex)).longValue();
			
			filteredNodesArray[(int)newNodeIndex] =
				nodes[(int)oldNodeIndex.longValue()];
		}
		
		ArrayList filteredEdgeFroms = new ArrayList();
		ArrayList filteredEdgeTos = new ArrayList();

		for (int i=0 ; i < edgeFroms.length ; i++)
		{
			Long edgeFrom = new Long(edgeFroms[i]);
			Long edgeTo = new Long(edgeTos[i]);

			if (nodeMap.containsKey(edgeFrom) &&
				nodeMap.containsKey(edgeTo))
			{
				filteredEdgeFroms.add(nodeMap.get(edgeFrom));
				filteredEdgeTos.add(nodeMap.get(edgeTo));
			}
		}
		
		long[] filteredEdgeFromsArray = new long[filteredEdgeFroms.size()];
		long[] filteredEdgeTosArray = new long[filteredEdgeTos.size()];
		
		for (int i=0 ; i < filteredEdgeFroms.size() ; i++)
		{
			filteredEdgeFromsArray[i] = ((Long)filteredEdgeFroms.get(i)).longValue();
			filteredEdgeTosArray[i] = ((Long)filteredEdgeTos.get(i)).longValue();
		}

		set(filteredNodesECD, ObjectIlaFromArray.create(filteredNodesArray));
		set(filteredEdgeFromsECD, LongIlaFromArray.create(filteredEdgeFromsArray));
		set(filteredEdgeTosECD, LongIlaFromArray.create(filteredEdgeTosArray));
	}
}