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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.BranchProxy;
import tfw.tsm.Root;
import tfw.tsm.RootProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.DoubleIlaECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

public class NodeEdgeConverter extends TriggeredConverter
{
	private final Root root;
	private final ObjectIlaECD nodesECD;
	private final DoubleIlaECD nodeXsECD;
	private final DoubleIlaECD nodeYsECD;
	private final ObjectIlaECD edgeFromsECD;
	private final ObjectIlaECD edgeTosECD;
	
	public NodeEdgeConverter(Root root, StatelessTriggerECD triggerECD,
		ObjectIlaECD nodesECD, DoubleIlaECD nodeXsECD, DoubleIlaECD nodeYsECD,
		ObjectIlaECD edgeFromsECD, ObjectIlaECD edgeTosECD)
	{
		super("NodeEdgeConverter",
			triggerECD,
			null,
			new EventChannelDescription[] {nodesECD, nodeXsECD, nodeYsECD,
				edgeFromsECD, edgeTosECD});
		
		this.root = root;
		this.nodesECD = nodesECD;
		this.nodeXsECD = nodeXsECD;
		this.nodeYsECD = nodeYsECD;
		this.edgeFromsECD = edgeFromsECD;
		this.edgeTosECD = edgeTosECD;
	}
	
	protected void convert()
	{
		Set nodes = new HashSet();
		Map nodeXs = new HashMap();
		Map nodeYs = new HashMap();
		ArrayList edgeFroms = new ArrayList();
		ArrayList edgeTos = new ArrayList();
		
		long depth = createLists(new RootProxy(root), nodes, nodeXs, nodeYs,
			edgeFroms, edgeTos, 0.0, 1.0, 0);
		
		Object[] n = nodes.toArray();
		double[] x = new double[n.length];
		double[] y = new double[n.length];
		
		for (int i=0 ; i < n.length ; i++)
		{
			x[i] = ((Double)nodeXs.get(n[i])).doubleValue();
			y[i] = ((Long)nodeYs.get(n[i])).doubleValue() / (double)depth;
		}
		set(nodesECD, ObjectIlaFromArray.create(n));
		set(edgeFromsECD, ObjectIlaFromArray.create(edgeFroms.toArray()));
		set(edgeTosECD, ObjectIlaFromArray.create(edgeTos.toArray()));
		set(nodeXsECD, DoubleIlaFromArray.create(x));
		set(nodeYsECD, DoubleIlaFromArray.create(y));
	}
	
	private static long createLists(Object proxy, Set nodes,
		Map nodeXs, Map nodeYs, ArrayList edgeFroms, ArrayList edgeTos,
		double min, double max, long depth)
	{
		long returnDepth = depth;
		
		if (nodes.add(proxy))
		{
			nodeXs.put(proxy, new Double((max - min) / 2.0 + min));
			nodeYs.put(proxy, new Long(depth));
		}
		
		if (proxy instanceof RootProxy)
		{
			RootProxy rootProxy = (RootProxy)proxy;
			
			Object[] childProxies = rootProxy.getChildProxies();
			double delta = (max - min) / childProxies.length;
			
			for (int i=0 ; i < childProxies.length ; i++)
			{
				double childMin = min + delta * i;
				double childMax = childMin + delta;
				
				edgeFroms.add(rootProxy);
				edgeTos.add(childProxies[i]);
				
				long childDepth = createLists(childProxies[i], nodes,
					nodeXs, nodeYs, edgeFroms, edgeTos, childMin, childMax,
					depth+1);
				
				if (childDepth > returnDepth)
				{
					returnDepth = childDepth;
				}
			}
		}
		else if (proxy instanceof BranchProxy)
		{
			BranchProxy branchProxy = (BranchProxy)proxy;
			
			Object[] childProxies = branchProxy.getChildProxies();
			double delta = (max - min) / childProxies.length;
			
			for (int i=0 ; i < childProxies.length ; i++)
			{
				double childMin = min + delta * i;
				double childMax = childMin + delta;
				
				edgeFroms.add(branchProxy);
				edgeTos.add(childProxies[i]);
				
				long childDepth = createLists(childProxies[i], nodes,
					nodeXs, nodeYs, edgeFroms, edgeTos, childMin, childMax,
					depth+1);
				
				if (childDepth > returnDepth)
				{
					returnDepth = childDepth;
				}
			}
		}
		
		return(returnDepth);
	}
}