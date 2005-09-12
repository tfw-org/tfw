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
package tfw.visualizer.test;

import java.util.Arrays;

import junit.framework.TestCase;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;
import tfw.immutable.ilm.doubleilm.test.DoubleIlmCheck;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.EventChannelProxy;
import tfw.tsm.Initiator;
import tfw.tsm.MultiplexedBranch;
import tfw.tsm.MultiplexedBranchFactory;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.RootProxy;
import tfw.tsm.Synchronizer;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.TriggeredConverter;
import tfw.tsm.Validator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;
import tfw.visualizer.NormalXYDoubleIlmFromGraph;
import tfw.visualizer.ProxyNameComparator;
import tfw.visualizer.graph.Graph;
import tfw.visualizer.graph.GraphEdgeEitherClassFilter;
import tfw.visualizer.graph.GraphFromArrays;
import tfw.visualizer.graph.GraphFromRootProxy;
import tfw.visualizer.graph.GraphNodeClassFilter;

public class NormalXYDoubleIlmFromGraphTest extends TestCase
{
	public void testNormalXYDoubleIlmFromGraph() throws Exception
	{
		BasicTransactionQueue basicTransactionQueue =
			new BasicTransactionQueue();
				
		StatelessTriggerECD triggerECD = new StatelessTriggerECD("trigger");
		ObjectECD objectECD = new ObjectECD("object");
		IntegerECD integer1ECD = new IntegerECD("integer1");
		IntegerECD integer2ECD = new IntegerECD("integer2");
		ObjectIlaECD objectIlaECD = new ObjectIlaECD("multiEventChannel2");
		
		BranchFactory branchFactory = new BranchFactory();
		branchFactory.addEventChannel(triggerECD);
		branchFactory.addEventChannel(objectIlaECD);
		Branch branch = branchFactory.create("branch");
		
		Commit commit = new Commit(
			"commit",
			new EventChannelDescription[] {integer2ECD},
			null,
			null)
		{
			protected void commit() {}
		};
		
		Converter converter = new Converter(
			"converter",
			new EventChannelDescription[] {integer2ECD},
			null,
			null)
		{
			protected void convert() {}
		};
		
		Initiator initiator = new Initiator(
			"initiator",
			new EventChannelDescription[] {integer1ECD, triggerECD});
		
		MultiplexedBranchFactory multiplexedBranchFactory =
			new MultiplexedBranchFactory();
		multiplexedBranchFactory.addMultiplexer(objectECD, objectIlaECD);
		MultiplexedBranch multiplexedBranch =
			multiplexedBranchFactory.create("multiplexedBranch");
		
		RootFactory rootFactory = new RootFactory();
		rootFactory.addEventChannel(integer1ECD);
		rootFactory.addEventChannel(integer2ECD);
		Root root = rootFactory.create("root", basicTransactionQueue);
		
		Synchronizer synchronizer = new Synchronizer(
			"synchronizer",
			new EventChannelDescription[] {integer1ECD},
			new EventChannelDescription[] {integer2ECD},
			null,
			null)
		{
			protected void convertAToB() {}
			protected void convertBToA() {}
		};
		
		TriggeredCommit triggeredCommit = new TriggeredCommit(
			"triggeredCommit",
			triggerECD,
			null,
			null)
		{
			protected void commit() {}
		};
		
		TriggeredConverter triggeredConverter = new TriggeredConverter(
		"triggeredConverter",
			triggerECD,
			null,
			null)
		{
			protected void convert() {}
		};
		
		Validator validator = new Validator(
			"validator",
			new EventChannelDescription[] {integer1ECD, integer2ECD},
			null,
			null)
		{
			protected void validateState() {}
		};
		
		root.add(branch);
		branch.add(validator);
		branch.add(triggeredConverter);
		branch.add(triggeredCommit);
		branch.add(synchronizer);
		branch.add(multiplexedBranch);
		branch.add(initiator);
		branch.add(converter);
		branch.add(commit);
		
		Graph graph = GraphFromRootProxy.create(new RootProxy(root));
		
		Object[] nodes = new Object[(int)graph.nodesLength()];
		Object[] froms = new Object[(int)graph.edgesLength()];
		Object[] tos = new Object[(int)graph.edgesLength()];

		graph.toArray(nodes, 0, 0, (int)graph.nodesLength(), froms, tos, 0, 0, (int)graph.edgesLength());
		Arrays.sort(nodes, ProxyNameComparator.INSTANCE);
		
		Graph sortedGraph = GraphFromArrays.create(nodes, froms, tos);

		DoubleIlm checkNormalXYs = DoubleIlmFromArray.create(new double[][] {
			{3.0/4.0, 3.0/11.0, 4.0/11.0, 1.0/4.0, 2.0/4.0, 1.0/11.0, 2.0/11.0,
				5.0/11.0, 6.0/11.0, 1.0/2.0, 7.0/11.0, 8.0/11.0, 9.0/11.0, 10.0/11.0},
			{2.0/4.0, 3.0/4.0, 3.0/4.0, 2.0/4.0, 2.0/4.0, 3.0/4.0, 3.0/4.0,
				3.0/4.0, 3.0/4.0, 1.0/4.0, 3.0/4.0, 3.0/4.0, 3.0/4.0, 3.0/4.0}});

		DoubleIlm normalXYs = NormalXYDoubleIlmFromGraph.create(sortedGraph);

		DoubleIlmCheck.check(checkNormalXYs, normalXYs);
		
		// Remove data flow edges and test.
		
		DoubleIlm checkNormalXYs1 = DoubleIlmFromArray.create(new double[][] {
			{3.0/6.0, 2.0/6.0+1.0/27.0, 2.0/6.0+2.0/27.0,
				1.0/6.0, 3.0/6.0, 2.0/3.0+1.0/6.0,
				1.0/6.0, 2.0/6.0+3.0/27.0, 2.0/6.0+4.0/27.0,
				3.0/6.0, 2.0/6.0+5.0/27.0, 2.0/6.0+6.0/27.0,
				2.0/6.0+7.0/27.0, 2.0/6.0+8.0/27.0},
			{2.0/6.0+2.0/12.0, 2.0/6.0+3.0/12.0, 2.0/6.0+3.0/12.0,
				1.0/6.0, 1.0/6.0, 1.0/6.0,
				3.0/6.0, 2.0/6.0+3.0/12.0, 2.0/6.0+3.0/12.0,
				2.0/6.0+1.0/12.0, 2.0/6.0+3.0/12.0, 2.0/6.0+3.0/12.0,
				2.0/6.0+3.0/12.0, 2.0/6.0+3.0/12.0}});

		DoubleIlm normalXYs1 = NormalXYDoubleIlmFromGraph.create(
			GraphEdgeEitherClassFilter.create(sortedGraph, EventChannelProxy.class));

		DoubleIlmCheck.check(checkNormalXYs1, normalXYs1);
		
		// Remove event channel descriptions and test.
		
		DoubleIlm checkNormalXYs2 = DoubleIlmFromArray.create(new double[][] {
			{1.0/2.0, 1.0/9.0, 2.0/9.0, 0.0, 0.0, 0.0, 0.0,
				3.0/9.0, 4.0/9.0, 1.0/2.0, 5.0/9.0, 6.0/9.0, 7.0/9.0, 8.0/9.0},
			{2.0/4.0, 3.0/4.0, 3.0/4.0, 0.0, 0.0, 0.0, 0.0,
				3.0/4.0, 3.0/4.0, 1.0/4.0, 3.0/4.0, 3.0/4.0, 3.0/4.0, 3.0/4.0}});

		DoubleIlm normalXYs2 = NormalXYDoubleIlmFromGraph.create(
			GraphNodeClassFilter.create(sortedGraph, EventChannelProxy.class));
		
		DoubleIlmCheck.check(checkNormalXYs2, normalXYs2);
			
try {
double[][] cn = checkNormalXYs2.toArray();
double[][] n = normalXYs2.toArray();
for (int i=0 ; i < n[0].length ; i++)
System.out.println("x["+i+"]="+n[0][i]+" cx["+i+"]="+cn[0][i]);
for (int i=0 ; i < n[1].length ; i++)
System.out.println("y["+i+"]="+n[1][i]+" cy["+i+"]="+cn[1][i]);
} catch(Exception e) {e.printStackTrace();}
	}
}