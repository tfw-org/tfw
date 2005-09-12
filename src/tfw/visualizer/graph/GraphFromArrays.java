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

import tfw.immutable.DataInvalidException;

public class GraphFromArrays
{
	private GraphFromArrays() {}
	
	public static Graph create(Object[] nodes, Object[] froms, Object[] tos)
	{
		return(new MyGraph(nodes, froms, tos));
	}
	
	private static class MyGraph implements Graph
	{
		private final Object[] nodes;
		private final Object[] froms;
		private final Object[] tos;
		
		public MyGraph(Object[] nodes, Object[] froms, Object[] tos)
		{
			this.nodes = (Object[])nodes.clone();
			this.froms = (Object[])froms.clone();
			this.tos = (Object[])tos.clone();
		}
		
		public long nodesLength()
		{
			return(nodes.length);
		}
		
		public long edgesLength()
		{
			return(froms.length);
		}
		
		public void toArray(Object[] nodes, int nodesOffset, long nodesStart,
			int nodesLength, Object[] edgeFroms, Object[] edgeTos, int edgesOffset,
			long edgesStart, int edgesLength) throws DataInvalidException
		{
			System.arraycopy(this.nodes, (int)nodesStart, nodes, nodesOffset, nodesLength);
			System.arraycopy(this.froms, (int)edgesStart, edgeFroms, edgesOffset, edgesLength);
			System.arraycopy(this.tos, (int)edgesStart, edgeTos, edgesOffset, edgesLength);
		}
	}
}