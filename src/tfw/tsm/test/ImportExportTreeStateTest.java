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

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchFactory;
import tfw.tsm.EventChannelState;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TreeComponent;
import tfw.tsm.TreeState;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;

import junit.framework.TestCase;


/**
 *
 */
public class ImportExportTreeStateTest extends TestCase
{
    public void testExportTreeState() throws Exception
    {
        String rootName = "MyRoot";
        String childName = "Child Tree";
        StringECD stringECD = new StringECD("ecd1");
        EventChannelState state = new EventChannelState(stringECD, "Hello");
        BasicTransactionQueue queue = new BasicTransactionQueue();

        RootFactory rf = new RootFactory();
        rf.addEventChannel(stringECD, state.getState());

        final Root root = rf.create(rootName, queue);

        try
        {
            root.getTreeState();
            fail("getTreeState() accepted call outside transaction thread");
        }
        catch (IllegalStateException expected)
        {
            //System.out.println(expected);
        }

        TreeStateRunnable runnable = new TreeStateRunnable(root);

        queue.add(runnable);
        queue.waitTilEmpty();

        assertNotNull("getTreeState() returned null", runnable.treeState);
        assertEquals("getTreeState() returned state with wrong name", rootName,
            runnable.treeState.getName());

        EventChannelState[] ecds = runnable.treeState.getState();
        assertNotNull("ecds == null", ecds);
        assertEquals("Wrong number of event channels", 1, ecds.length);
        assertEquals("Wrong event channel state returned", state, ecds[0]);

        TreeState[] children = runnable.treeState.getChildren();
        assertNotNull("children == null", children);
        assertEquals("Wrong number of children", 0, children.length);

        IntegerECD integerECD = new IntegerECD("integer");
        Integer integerState = new Integer(123456);
        BranchFactory bf = new BranchFactory();
        bf.addEventChannel(integerECD, integerState);

        Branch branch = bf.create("Child Tree");
        root.add(branch);
        queue.add(runnable);
        queue.waitTilEmpty();

        children = runnable.treeState.getChildren();
        assertNotNull("children == null", children);
        assertEquals("Wrong number of children", 1, children.length);

        try
        {
            root.setTreeState(null);
            fail("setTreeState() accepted null value");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            root.setTreeState(new TreeState("wrongname", null, null));
            fail("setTreeState() accepted wrong name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

		try
		{
			root.setTreeState(new TreeState(rootName,
					new EventChannelState[]
					{
						new EventChannelState(integerECD, new Integer(5))
					}, null));

			fail("setTreeState() accepted wrong event channel state");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}

		EventChannelStateBuffer buff = new EventChannelStateBuffer();
		buff.put(integerECD, new Integer(654321));

		TreeState childTS = new TreeState("wrongname", buff.toArray(), null);
		buff.clear();
		buff.put(stringECD, "Goodbye");

		TreeState rootTS = new TreeState(rootName, buff.toArray(),
				new TreeState[]{ childTS });

		try
		{
			root.setTreeState(rootTS);
			fail("setTreeState() accepted wrong child tree state name");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}

		buff.clear();
		buff.put(integerECD, new Integer(654321));

		childTS = new TreeState(childName, buff.toArray(), null);
		buff.clear();
		buff.put(stringECD, "Goodbye");

		rootTS = new TreeState(rootName, buff.toArray(),
				new TreeState[]{ childTS });


        root.setTreeState(rootTS);
        queue.add(runnable);
        queue.waitTilEmpty();
		//System.out.println(rootTS);
		//System.out.println(runnable.treeState);
        assertEquals("TreeState not equal",rootTS, runnable.treeState);
    }


    private class TreeStateRunnable implements Runnable
    {
        public TreeState treeState = null;
        private final TreeComponent component;

        public TreeStateRunnable(TreeComponent component)
        {
            this.component = component;
        }

        public void run()
        {
            treeState = component.getTreeState();
        }
    }
}
