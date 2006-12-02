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

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchComponent;
import tfw.tsm.BranchFactory;
import tfw.tsm.EventChannelState;
import tfw.tsm.EventChannelStateBuffer;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TreeState;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;

/**
 * 
 */
public class ImportExportTreeStateTest extends TestCase
{
    public void testDefaultExportTreeState() throws Exception
    {
        String rootName = "MyRoot";
        String childName = "Child Tree";
        StringECD stringECD = new StringECD("ecd1");
        StringECD stringECDNullState = new StringECD("stringNullState");
        EventChannelState state = new EventChannelState(stringECD, "Hello");
        BasicTransactionQueue queue = new BasicTransactionQueue();

        RootFactory rf = new RootFactory();
        rf.addEventChannel(stringECD, state.getState());
        rf.addEventChannel(stringECDNullState, null);

        final Root root = rf.create(rootName, queue);

        try
        {
            root.getTreeState();
            fail("getTreeState() accepted call outside transaction thread");
        }
        catch (IllegalStateException expected)
        {
            // System.out.println(expected);
        }

        GetTreeStateRunnable getStateRunnable = new GetTreeStateRunnable(root);

        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        assertNotNull("getTreeState() returned null",
                getStateRunnable.treeState);
        assertEquals("getTreeState() returned state with wrong name", rootName,
                getStateRunnable.treeState.getName());

        EventChannelState[] ecds = getStateRunnable.treeState.getState();
        assertNotNull("ecds == null", ecds);
        /*
         * We should only get the state from stringECD because the other two
         * have no state.
         */
        assertEquals("Wrong number of event channels", 1, ecds.length);
        assertEquals("Wrong event channel state returned", state, ecds[0]);

        TreeState[] children = getStateRunnable.treeState.getChildren();
        assertNotNull("children == null", children);
        assertEquals("Wrong number of children", 0, children.length);

        IntegerECD integerECD = new IntegerECD("integer");
        Integer integerState = new Integer(123456);
        BranchFactory bf = new BranchFactory();
        bf.addEventChannel(integerECD, integerState);

        Branch branch = bf.create("Child Tree");
        root.add(branch);
        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        children = getStateRunnable.treeState.getChildren();
        assertNotNull("children == null", children);
        assertEquals("Wrong number of children", 1, children.length);

        SetTreeStateRunnable setStateRunnable = new SetTreeStateRunnable(root,
                null);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted null value");

        setStateRunnable = new SetTreeStateRunnable(root, new TreeState(
                "wrongname", null, null));
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted wrong event channel state");

        EventChannelStateBuffer buff = new EventChannelStateBuffer();
        buff.put(integerECD, new Integer(654321));

        TreeState childTS = new TreeState("wrongname", buff.toArray(), null);
        buff.clear();
        buff.put(stringECD, "Goodbye");

        TreeState rootTS = new TreeState(rootName, buff.toArray(),
                new TreeState[] { childTS });

        
        setStateRunnable = new SetTreeStateRunnable(root, rootTS);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted wrong child tree state name");

        buff.clear();
        buff.put(integerECD, new Integer(654321));

        childTS = new TreeState(childName, buff.toArray(), null);
        buff.clear();
        buff.put(stringECD, "Goodbye");

        rootTS = new TreeState(rootName, buff.toArray(),
                new TreeState[] { childTS });

        setStateRunnable = new SetTreeStateRunnable(root, rootTS);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();
        // System.out.println(rootTS);
        // System.out.println(runnable.treeState);
        assertEquals("TreeState not equal", rootTS, getStateRunnable.treeState);
    }

    public void testCustomTagExportImportTreeState()
    {
        RootFactory rf = new RootFactory();
        String tag1 = "tag1";
        String tag2 = "tag2";
        StringECD ecd1 = new StringECD("str_ecd_1");
        IntegerECD ecd2 = new IntegerECD("int_ecd_1");
        EventChannelState state1 = new EventChannelState(ecd1, "hello");
        EventChannelState state2 = new EventChannelState(ecd2, new Integer(123));
        rf.addEventChannel(ecd1, state1.getState());
        try
        {
            rf.addExportTag(null, tag1);
            fail("addExportTag() accepted null ECD");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
        try
        {
            rf.addExportTag(ecd1, null);
            fail("addExportTag() accepted null tag");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
        try
        {
            rf.addExportTag(ecd1, "");
            fail("addExportTag() accepted empty tag");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }
        try
        {
            rf.addExportTag(ecd2, tag1);
            fail("addExportTag() accepted unknown ECD");
        }
        catch (IllegalArgumentException expected)
        {
            // System.out.println(expected);
        }

        rf.addExportTag(ecd1, tag1);
        rf.addEventChannel(ecd2, state2.getState());
        rf.addExportTag(ecd1, tag2);
        rf.addExportTag(ecd2, tag2);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("TestRoot", queue);
        GetTreeStateRunnable tsr = new GetTreeStateRunnable(root, null);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();
        assertNotNull("getTreeState() accepted null tag", tsr.exception);

        tsr = new GetTreeStateRunnable(root, tag1);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertNotNull("getTreeState() returned null", tsr.treeState);
        EventChannelState[] ecs = tsr.treeState.getState();
        assertEquals("The wrong number of event channel state returned", 1,
                ecs.length);
        assertEquals("The wrong event channel description was returned", state1
                .getEventChannelName(), ecs[0].getEventChannelName());
        assertEquals("The wrong event channel state was returned", state1
                .getState(), ecs[0].getState());

        tsr = new GetTreeStateRunnable(root, tag2);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertNotNull("getTreeState() returned null", tsr.treeState);
        ecs = tsr.treeState.getState();
        assertEquals("The wrong number of event channel state returned", 2,
                ecs.length);
    }

    private class GetTreeStateRunnable implements Runnable
    {
        public TreeState treeState = null;

        public RuntimeException exception = null;

        private final BranchComponent component;

        private final String exportTag;

        public GetTreeStateRunnable(BranchComponent component)
        {
            this(component, BranchComponent.DEFAULT_EXPORT_TAG);
        }

        public GetTreeStateRunnable(BranchComponent component, String tag)
        {
            this.component = component;
            this.exportTag = tag;
        }

        public void run()
        {
            try
            {
                treeState = component.getTreeState(this.exportTag);
            }
            catch (RuntimeException e)
            {
                this.exception = e;
            }
        }
    }

    private class SetTreeStateRunnable implements Runnable
    {
        public TreeState treeState = null;

        public RuntimeException exception = null;

        private final BranchComponent component;

        public SetTreeStateRunnable(BranchComponent component, TreeState treeState)
        {
            this.component = component;
            this.treeState = treeState;
        }

        public void run()
        {
            try
            {
                component.setTreeState(this.treeState, false, false);
            }
            catch (RuntimeException e)
            {
                this.exception = e;
            }
        }
    }
}
