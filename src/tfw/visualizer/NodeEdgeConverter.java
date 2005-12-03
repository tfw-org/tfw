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

import tfw.tsm.Root;
import tfw.tsm.RootProxy;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphECD;
import tfw.visualizer.graph.GraphFromRootProxy;

public class NodeEdgeConverter extends TriggeredConverter
{
	private final Root root;
	private final GraphECD graphECD;
	
	public NodeEdgeConverter(Root root, StatelessTriggerECD triggerECD,
		GraphECD graphECD)
	{
		super("NodeEdgeConverter",
			triggerECD,
			null,
			new ObjectECD[] {graphECD});
		
		this.root = root;
		this.graphECD = graphECD;
	}
	
	protected void convert()
	{
		Graph graphFromRootProxy = GraphFromRootProxy.create(new RootProxy(root));
		
		set(graphECD, graphFromRootProxy);
	}
}