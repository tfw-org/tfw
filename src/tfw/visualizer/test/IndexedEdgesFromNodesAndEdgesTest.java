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
package tfw.visualizer.test;

import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ila.longila.test.LongIlaCheck;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.test.IlaTestDimensions;
import tfw.visualizer.IndexedEdgesFromNodesAndEdges;

public class IndexedEdgesFromNodesAndEdgesTest extends TestCase
{
	public void testIndexedEdgesFromNodesAndEdges() throws Exception
	{
		Object[] nodesArray = new Object[] {
			new Object(),
			new Object(),
			new Object(),
			new Object(),
			new Object()};
		Object[] edgesArray = new Object[] {
			nodesArray[0],
			nodesArray[1],
			nodesArray[2],
			nodesArray[3],
			nodesArray[4],
			nodesArray[3],
			nodesArray[2],
			nodesArray[1],
			nodesArray[0]};
		
		ObjectIla nodes = ObjectIlaFromArray.create(nodesArray);
		ObjectIla edges = ObjectIlaFromArray.create(edgesArray);
		
		long[] indexes = new long[] {0, 1, 2, 3, 4, 3, 2, 1, 0};
		
		LongIla targetIla = LongIlaFromArray.create(indexes);
		LongIla actualIla = IndexedEdgesFromNodesAndEdges.create(nodes, edges);
		
        final long epsilon = (long) 0.0;
        LongIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
	}
}