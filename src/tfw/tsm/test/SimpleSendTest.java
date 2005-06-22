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
package tfw.tsm.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SimpleSendTest extends TestCase
{
//	public SimpleSendTest(){
//		super()	;
//	}
//	
//	public SimpleSendTest(String test){
//		super(test);
//	}
//	
//	private final String answer = "Hello World";
//	private String result = null;
//
//	private String[] eventChannels = new String[]{"a"};
//	private Initiator initiator = new Initiator("Initiator"
//			, eventChannels);
//	private Commit commit = new Commit("Commit", eventChannels)
//	{
//		protected void commit()
//		{
//			result = (String)get("a");
//		}
//	};
//
//	public void setUp()
//	{
//	}
//
//	public void testSimpleLeafConnections()
//		throws InterruptedException, InvocationTargetException
//	{
//		BranchFactory nf = new BranchFactory();
//		nf.addTerminator("a");
//		nf.setRoot(true);
//		Branch branch = nf.create("Test branch");
//		branch.add(initiator);
//		branch.add(commit);
//		SynchronizeThread.sync(10);
//
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("initial connections", answer, result);
//		
//		branch.remove(commit);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("disconnect sink", null, result);
//
//		result = null;
//		branch.add(commit);
//		SynchronizeThread.sync(10);
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("reconnect sink", answer, result);
//
//		branch.remove(initiator);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("source removal", null, result);
//
//		branch.add(initiator);
//		SynchronizeThread.sync(10);
//		assertEquals("fire on connect", answer, result);
//	}
//
//	public void testThreeLayerBranchConnections()
//		throws InterruptedException, InvocationTargetException
//	{
//		BranchFactory nf = new BranchFactory();
//		nf.addTerminator("a");
//		nf.setRoot(true);
//		Branch topBranch = nf.create("Top Branch");
//		nf.clear();
//		Branch middleBranch = nf.create("Middle Branch");
//		topBranch.add(initiator);
//		middleBranch.add(commit);
//		topBranch.add(middleBranch);
//
//		SynchronizeThread.sync(10);
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("initial connections", answer, result);
//
//		middleBranch.remove(commit);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("disconnect sink", null, result);
//
//		middleBranch.add(commit);
//		SynchronizeThread.sync(10);
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("reconnect sink", answer, result);
//
//		topBranch.remove(initiator);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("source removal", null, result);
//
//		topBranch.add(initiator);
//		SynchronizeThread.sync(10);
//		assertEquals("fire on connect", answer, result);
//	}
//
//	public void testInitialState()
//		throws InterruptedException, InvocationTargetException
//	{
//		BranchFactory nf = new BranchFactory();
//		nf.addTerminator("a",answer, true, true);
//		nf.setRoot(true);
//		Branch branch = nf.create("Test branch");
//		branch.add(commit);
//
//		SynchronizeThread.sync(10);
//		assertEquals("Initial state", answer, result);
//	}
//
//	public void testTranslation()
//		throws InterruptedException, InvocationTargetException
//	{
//		BranchFactory nf = new BranchFactory();
//		nf.addTerminator("b");
//		nf.setRoot(true);
//		Branch topBranch = nf.create("Top Branch");
//		nf.clear();
//		nf.addTranslation("a", "b");
//		Branch middleBranch1 = nf.create("Middle Branch");
//		middleBranch1.add(commit);
//
//		nf.clear();
//		nf.addTranslation("a", "b");
//		Branch middleBranch2 = nf.create("Middle Branch");
//		middleBranch2.add(initiator);
//
//		topBranch.add(middleBranch1);
//		topBranch.add(middleBranch2);
//
//		SynchronizeThread.sync(10);
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("initial connections", answer, result);
//
//		middleBranch1.remove(commit);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("disconnect sink", null, result);
//
//		middleBranch1.add(commit);
//		SynchronizeThread.sync(10);
//		initiator.set("a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("reconnect sink", answer, result);
//
//		middleBranch2.remove(initiator);
//		SynchronizeThread.sync(10);
//		result = null;
//		initiator.set( "a", answer);
//		SynchronizeThread.sync(10);
//		assertEquals("source removal", null, result);
//
//		middleBranch2.add(initiator);
//		SynchronizeThread.sync(10);
//		assertEquals("fire on connect", answer, result);
//	}
//
//
//	public void testOverLap()
//	{
//		BranchFactory nf = new BranchFactory();
//		nf.addTerminator("a");
//		nf.addTranslation("a","b");
//		try
//		{
//			Branch Branch = nf.create("Overlaping");
//		}
//		catch (IllegalArgumentException expected)
//		{
//		}
//
//	}
//
	public static Test suite()
	{
//		TestSuite suite = new TestSuite();
//		suite.addTest(new SimpleSendTest("testOverLap"));
//		suite.addTest(new SimpleSendTest("testThreeLayerBranchConnections"));
//		suite.addTest(new SimpleSendTest("testInitialState"));
//		return suite;
		return new TestSuite(SimpleSendTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
		System.exit(0);
	}
}
