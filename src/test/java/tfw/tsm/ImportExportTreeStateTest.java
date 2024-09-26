package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class ImportExportTreeStateTest {
    @Test
    void testDefaultExportTreeState() throws Exception {
        String rootName = "Root";
        String childName = "Child Tree";
        StringECD stringECD = new StringECD("ecd1");
        StringECD stringECDNullState = new StringECD("stringNullState");
        EventChannelState state = new EventChannelState(stringECD, "Hello");
        BasicTransactionQueue queue = new BasicTransactionQueue();

        RootFactory rf = new RootFactory();
        rf.addEventChannel(stringECD, state.getState());
        rf.addEventChannel(stringECDNullState, null);

        final Root root = rf.create(rootName, queue);

        try {
            root.getTreeState();
            fail("getTreeState() accepted call outside transaction thread");
        } catch (IllegalStateException expected) {
            // System.out.println(expected);
        }

        GetTreeStateRunnable getStateRunnable = new GetTreeStateRunnable(root);

        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        assertNotNull(getStateRunnable.treeState, "getTreeState() returned null");
        assertEquals(rootName, getStateRunnable.treeState.getName(), "getTreeState() returned state with wrong name");

        EventChannelState[] ecds = getStateRunnable.treeState.getState();
        assertNotNull(ecds, "ecds == null");
        /*
         * We should only get the state from stringECD because the other two
         * have no state.
         */
        assertEquals(1, ecds.length, "Wrong number of event channels");
        assertEquals(state, ecds[0], "Wrong event channel state returned");

        TreeState[] children = getStateRunnable.treeState.getChildren();
        assertNotNull(children, "children == null");
        assertEquals(0, children.length, "Wrong number of children");

        IntegerECD integerECD = new IntegerECD("integer");
        Integer integerState = 123456;
        BranchFactory bf = new BranchFactory();
        bf.addEventChannel(integerECD, integerState);

        Branch branch = bf.create("Child Tree");
        root.add(branch);
        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        children = getStateRunnable.treeState.getChildren();
        assertNotNull(children, "children == null");
        assertEquals(1, children.length, "Wrong number of children");

        SetTreeStateRunnable setStateRunnable = new SetTreeStateRunnable(root, null);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted null value");

        setStateRunnable = new SetTreeStateRunnable(root, new TreeState("wrongname", null, null));
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted wrong event channel state");

        EventChannelStateBuffer buff = new EventChannelStateBuffer();
        buff.put(integerECD, 654321);

        TreeState childTS = new TreeState("wrongname", buff.toArray(), null);
        buff.clear();
        buff.put(stringECD, "Goodbye");

        TreeState rootTS = new TreeState(rootName, buff.toArray(), new TreeState[] {childTS});

        setStateRunnable = new SetTreeStateRunnable(root, rootTS);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        assertNotNull("setTreeState() accepted wrong child tree state name");

        buff.clear();
        buff.put(integerECD, 654321);

        childTS = new TreeState(childName, buff.toArray(), null);
        buff.clear();
        buff.put(stringECD, "Goodbye");

        rootTS = new TreeState(rootName, buff.toArray(), new TreeState[] {childTS});

        setStateRunnable = new SetTreeStateRunnable(root, rootTS);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();
        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();
        // System.out.println(rootTS);
        // System.out.println(runnable.treeState);
        assertEquals(rootTS, getStateRunnable.treeState, "TreeState not equal");
    }

    @Test
    void testCustomTagExportImportTreeState() {
        RootFactory rf = new RootFactory();
        String tag1 = "tag1";
        String tag2 = "tag2";
        StringECD ecd1 = new StringECD("str_ecd_1");
        IntegerECD ecd2 = new IntegerECD("int_ecd_1");
        EventChannelState state1 = new EventChannelState(ecd1, "hello");
        EventChannelState state2 = new EventChannelState(ecd2, 123);
        rf.addEventChannel(ecd1, state1.getState());
        try {
            rf.addExportTag(null, tag1);
            fail("addExportTag() accepted null ECD");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        try {
            rf.addExportTag(ecd1, null);
            fail("addExportTag() accepted null tag");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        try {
            rf.addExportTag(ecd1, "");
            fail("addExportTag() accepted empty tag");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        try {
            rf.addExportTag(ecd2, tag1);
            fail("addExportTag() accepted unknown ECD");
        } catch (IllegalArgumentException expected) {
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
        assertNotNull(tsr.exception, "getTreeState() accepted null tag");

        tsr = new GetTreeStateRunnable(root, tag1);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertNotNull(tsr.treeState, "getTreeState() returned null");
        EventChannelState[] ecs = tsr.treeState.getState();
        assertEquals(1, ecs.length, "The wrong number of event channel state returned");
        assertEquals(
                state1.getEventChannelName(),
                ecs[0].getEventChannelName(),
                "The wrong event channel description was returned");
        assertEquals(state1.getState(), ecs[0].getState(), "The wrong event channel state was returned");

        tsr = new GetTreeStateRunnable(root, tag2);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertNotNull(tsr.treeState, "getTreeState() returned null");
        ecs = tsr.treeState.getState();
        assertEquals(2, ecs.length, "The wrong number of event channel state returned");
    }

    private static class GetTreeStateRunnable implements Runnable {
        public TreeState treeState = null;

        public RuntimeException exception = null;

        private final BranchComponent component;

        private final String exportTag;

        public GetTreeStateRunnable(BranchComponent component) {
            this(component, BranchComponent.DEFAULT_EXPORT_TAG);
        }

        public GetTreeStateRunnable(BranchComponent component, String tag) {
            this.component = component;
            this.exportTag = tag;
        }

        public void run() {
            try {
                treeState = component.getTreeState(this.exportTag);
            } catch (RuntimeException e) {
                this.exception = e;
            }
        }
    }

    private static class SetTreeStateRunnable implements Runnable {
        public TreeState treeState = null;

        public RuntimeException exception = null;

        private final BranchComponent component;

        public SetTreeStateRunnable(BranchComponent component, TreeState treeState) {
            this.component = component;
            this.treeState = treeState;
        }

        public void run() {
            try {
                component.setTreeState(this.treeState, false, false);
            } catch (RuntimeException e) {
                this.exception = e;
            }
        }
    }
}
