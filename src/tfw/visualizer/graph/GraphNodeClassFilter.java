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
package tfw.visualizer.graph;

import tfw.check.Argument;
import tfw.immutable.DataInvalidException;

public class GraphNodeClassFilter
{
	private GraphNodeClassFilter() {}
	
	public static Graph create(Graph graph, Class classToRemove)
	{
		Argument.assertNotNull(graph, "graph");
		Argument.assertNotNull(classToRemove, "classToRemove");
		
		return(new MyGraph(graph, classToRemove));
	}
	
	private static class MyGraph implements Graph
	{
		private final Graph graph;
		private final Class classToRemove;
		
		public MyGraph(Graph graph, Class classToRemove)
		{
			this.graph = graph;
			this.classToRemove = classToRemove;
		}
		
		public long nodesLength()
		{
			return(graph.nodesLength());
		}
		
		public long edgesLength()
		{
			return(graph.edgesLength());
		}
		
		public void toArray(Object[] nodes, int nodesOffset, long nodesStart,
			int nodesLength, Object[] edgeFroms, Object[] edgeTos,
			int edgesOffset, long edgesStart, int edgesLength)
			throws DataInvalidException
		{
			graph.toArray(nodes, nodesOffset, nodesStart, nodesLength,
				edgeFroms, edgeTos, edgesOffset, edgesStart, edgesLength);
			
			int nodesEnd = nodesOffset + nodesLength;
			int edgesEnd = edgesOffset + edgesLength;
			
			for (int i=nodesOffset ; i < nodesEnd ; i++)
			{
				if (nodes[i] != null && nodes[i].getClass() == classToRemove)
				{
					nodes[i] = null;
				}
			}
			for (int i=edgesOffset ; i < edgesEnd ; i++)
			{
				if (edgeFroms[i] != null && edgeTos[i] != null &&
					(edgeFroms[i].getClass() == classToRemove ||
					edgeTos[i].getClass() == classToRemove))
				{
					edgeFroms[i] = null;
					edgeTos[i] = null;
				}
			}
		}
	}
}