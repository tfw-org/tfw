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

import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.objectila.ObjectIla;

public class IndexedEdgesFromNodesAndEdges
{
	public static LongIla create(ObjectIla nodes, ObjectIla edges)
	{
		return(new MyLongIla(nodes, edges));
	}
	
	private static class MyLongIla implements LongIla
	{
		private final ObjectIla nodes;
		private final ObjectIla edges;
		
		public MyLongIla(ObjectIla nodes, ObjectIla edges)
		{
			this.nodes = nodes;
			this.edges = edges;
		}
		
		public long length()
		{
			return(edges.length());
		}
		
		public long[] toArray()
			throws DataInvalidException
		{
			Object[] nodeArray = nodes.toArray();
			Object[] edgeArray = edges.toArray();
			long[] indexArray = new long[edgeArray.length];
			
			createIndexArray(nodeArray, edgeArray, indexArray, 0);
			
			return(indexArray);
		}
		
		public long[] toArray(long start, int length)
			throws DataInvalidException
		{
			Object[] nodeArray = nodes.toArray();
			Object[] edgeArray = edges.toArray(start, length);
			long[] indexArray = new long[edgeArray.length];
			
			createIndexArray(nodeArray, edgeArray, indexArray, 0);
			
			return(indexArray);
		}
		
		public void toArray(long[] array, int offset, long start, int length)
			throws DataInvalidException
		{
			Object[] nodeArray = nodes.toArray();
			Object[] edgeArray = edges.toArray(start, length);
			
			createIndexArray(nodeArray, edgeArray, array, offset);
		}
		
		private void createIndexArray(Object[] nodeArray, Object[] edgeArray,
			long[] indexArray, int indexArrayOffset)
		{
			for (int i=0 ; i < nodeArray.length ; i++)
			{
				for (int j=0 ; j < edgeArray.length ; j++)
				{
					if (nodeArray[i].equals(edgeArray[j]))
					{
						indexArray[j+indexArrayOffset] = i;
					}
				}
			}			
		}
	}
}