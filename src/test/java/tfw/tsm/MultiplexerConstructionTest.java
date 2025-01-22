package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;
import tfw.tsm.ecd.ila.ObjectIlaECD;

final class MultiplexerConstructionTest {
    private static final StringECD VALUE_ECD = new StringECD("value");
    private static final ObjectIlaECD MULTIVALUE_ECD = new ObjectIlaECD("multiValue");
    private static final StatelessTriggerECD TRIGGER_ECD = new StatelessTriggerECD("trigger");

    private final ValueCommit vc1 = new ValueCommit("vc1");
    private final ValueCommit vc2 = new ValueCommit("vc2");

    @Test
    void dynamicConstructionTest() {
        String value = "bob";
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(VALUE_ECD, MULTIVALUE_ECD);
        MultiplexedBranch multiBranch = mbf.create("multiBranch");
        multiBranch.add(vc1, 0);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        RootFactory rf = new RootFactory();
        rf.addEventChannel(MULTIVALUE_ECD, ObjectIlaFromArray.create(new String[] {value}));
        rf.addEventChannel(TRIGGER_ECD);
        Root root = rf.create("Test", queue);

        root.add(multiBranch);
        queue.waitTilEmpty();
    }

    @Test
    void nameSpaceSeparationTest() {
        String value0 = "bob";
        String value1 = "sally";
        MultiplexedBranchFactory mbf = new MultiplexedBranchFactory();
        mbf.addMultiplexer(VALUE_ECD, MULTIVALUE_ECD);
        MultiplexedBranch multiBranch = mbf.create("multiBranch");
        ValueCommit vc0 = new ValueCommit("vc");
        ValueCommit vc1 = new ValueCommit("vc");
        multiBranch.add(vc0, 0);
        multiBranch.add(vc1, 1);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        RootFactory rf = new RootFactory();
        rf.addEventChannel(MULTIVALUE_ECD, ObjectIlaFromArray.create(new String[] {value0, value1}));
        Root root = rf.create("Test", queue);
        root.add(multiBranch);
        queue.waitTilEmpty();
        assertThat(value0).isEqualTo(vc0.value);
        assertThat(value1).isEqualTo(vc1.value);
    }

    private static class ValueCommit extends Commit {
        String value = null;

        public ValueCommit(String name) {
            super(name, new StringECD[] {VALUE_ECD});
        }

        @Override
        protected void commit() {
            this.value = (String) get(VALUE_ECD);
        }
    }

    private class ComponentSwitchCommit extends TriggeredCommit {
        public final MultiplexedBranch mb;

        public ComponentSwitchCommit(MultiplexedBranch mb) {
            super("ComponentSwitchCommit", TRIGGER_ECD);
            this.mb = mb;
        }

        @Override
        protected void commit() {
            mb.remove(vc1);
            mb.add(vc2, 0);
        }
    }
}
