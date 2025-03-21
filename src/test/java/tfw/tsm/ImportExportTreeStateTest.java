package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.StringECD;

final class ImportExportTreeStateTest {
    @Test
    void defaultExportTreeStateTest() throws Exception {
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

        assertThatThrownBy(root::getTreeState)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This method can not be called from outside the transaction queue thread");

        GetTreeStateRunnable getStateRunnable = new GetTreeStateRunnable(root);

        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        assertThat(getStateRunnable.treeState).isNotNull();
        assertThat(rootName).isEqualTo(getStateRunnable.treeState.getName());

        EventChannelState[] ecds = getStateRunnable.treeState.getState();
        assertThat(ecds).isNotNull();
        /*
         * We should only get the state from stringECD because the other two
         * have no state.
         */
        assertThat(1).isEqualTo(ecds.length);
        assertThat(state).isEqualTo(ecds[0]);

        TreeState[] children = getStateRunnable.treeState.getChildren();
        assertThat(children).isNotNull();
        assertThat(0).isEqualTo(children.length);

        IntegerECD integerECD = new IntegerECD("integer");
        Integer integerState = 123456;
        BranchFactory bf = new BranchFactory();
        bf.addEventChannel(integerECD, integerState);

        Branch branch = bf.create("Child Tree");
        root.add(branch);
        queue.invokeLater(getStateRunnable);
        queue.waitTilEmpty();

        children = getStateRunnable.treeState.getChildren();
        assertThat(children).isNotNull();
        assertThat(children.length).isEqualTo(1);

        SetTreeStateRunnable setStateRunnable = new SetTreeStateRunnable(root, null);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();

        setStateRunnable = new SetTreeStateRunnable(root, new TreeState("wrongname", null, null));
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();

        EventChannelStateBuffer buff = new EventChannelStateBuffer();
        buff.put(integerECD, 654321);

        TreeState childTS = new TreeState("wrongname", buff.toArray(), null);
        buff.clear();
        buff.put(stringECD, "Goodbye");

        TreeState rootTS = new TreeState(rootName, buff.toArray(), new TreeState[] {childTS});

        setStateRunnable = new SetTreeStateRunnable(root, rootTS);
        queue.invokeLater(setStateRunnable);
        queue.waitTilEmpty();

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
        assertThat(rootTS).isEqualTo(getStateRunnable.treeState);
    }

    @Test
    void customTagExportImportTreeStateTest() {
        RootFactory rf = new RootFactory();
        String tag1 = "tag1";
        String tag2 = "tag2";
        StringECD ecd1 = new StringECD("str_ecd_1");
        IntegerECD ecd2 = new IntegerECD("int_ecd_1");
        EventChannelState state1 = new EventChannelState(ecd1, "hello");
        EventChannelState state2 = new EventChannelState(ecd2, 123);
        rf.addEventChannel(ecd1, state1.getState());

        assertThatThrownBy(() -> rf.addExportTag(null, tag1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ecd == null not allowed!");
        assertThatThrownBy(() -> rf.addExportTag(ecd1, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tag == null not allowed!");
        assertThatThrownBy(() -> rf.addExportTag(ecd1, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("tag.isEmpty() not allowed!");
        assertThatThrownBy(() -> rf.addExportTag(ecd2, tag1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "The event channel 'int_ecd_1' has not been added to this factory and therefore can not be tagged.");

        rf.addExportTag(ecd1, tag1);
        rf.addEventChannel(ecd2, state2.getState());
        rf.addExportTag(ecd1, tag2);
        rf.addExportTag(ecd2, tag2);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("TestRoot", queue);
        GetTreeStateRunnable tsr = new GetTreeStateRunnable(root, null);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();
        assertThat(tsr.exception).isNotNull();

        tsr = new GetTreeStateRunnable(root, tag1);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertThat(tsr.treeState).isNotNull();
        EventChannelState[] ecs = tsr.treeState.getState();
        assertThat(ecs.length).isEqualTo(1);
        assertThat(state1.getEventChannelName()).isEqualTo(ecs[0].getEventChannelName());
        assertThat(state1.getState()).isEqualTo(ecs[0].getState());

        tsr = new GetTreeStateRunnable(root, tag2);
        queue.invokeLater(tsr);
        queue.waitTilEmpty();

        assertThat(tsr.treeState).isNotNull();
        ecs = tsr.treeState.getState();
        assertThat(ecs.length).isEqualTo(2);
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

        @Override
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

        @Override
        public void run() {
            try {
                component.setTreeState(this.treeState, false, false);
            } catch (RuntimeException e) {
                this.exception = e;
            }
        }
    }
}
