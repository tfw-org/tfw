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
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaIterator;

public class NodeClassFilter
{
	private NodeClassFilter() {}
	
	public static ObjectIla create(ObjectIla nodes, Class classToRemove)
	{
		return(new MyObjectIla(nodes, classToRemove));
	}
	
	private static class MyObjectIla implements ObjectIla
	{
		private final ObjectIla nodes;
		private final Class classToRemove;
		
		private long length = -1;
		
		public MyObjectIla(ObjectIla nodes, Class classToRemove)
		{
			this.nodes = nodes;
			this.classToRemove = classToRemove;
		}
		
		public long length()
		{
			calculateLength();
			
			return(length);
		}
		
		public Object[] toArray()
			throws DataInvalidException
		{
			calculateLength();
			
			Object[] newNodesArray = new Object[(int)length];
			
			toArray(newNodesArray, 0, 0, newNodesArray.length);
			
			return(newNodesArray);
		}
		
		public Object[] toArray(long start, int length)
			throws DataInvalidException
		{
			Object[] newNodesArray = new Object[length];
			
			toArray(newNodesArray, 0, start, length);
			
			return(newNodesArray);
		}
		
		public void toArray(Object[] array, int offset, long start, int length)
			throws DataInvalidException
		{
			ObjectIlaIterator oii = new ObjectIlaIterator(nodes);
			long currentNode = 0;
			long endingNode = start + length;
			
			for (int i=0 ; oii.hasNext() && currentNode < endingNode ; )
			{
				Object node = oii.next();
				
				if (node.getClass() != classToRemove)
				{
					if (currentNode >= start)
					{
						array[i+offset] = node;
						i++;
					}
					
					currentNode++;
				}
			}
		}
		
		private void calculateLength()
		{
			if (length < 0)
			{			
				length = nodes.length();
				ObjectIlaIterator oii = new ObjectIlaIterator(nodes);
				
				try
				{
					for (int i=0 ; oii.hasNext() ; i++)
					{
						if (oii.next().getClass() == classToRemove)
						{
							length--;
						}
					}
				}
				catch (DataInvalidException die)
				{
					length = 0;
				}
			}
		}
	}
}