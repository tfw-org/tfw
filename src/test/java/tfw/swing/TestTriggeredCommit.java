package tfw.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tfw.check.Argument;
import tfw.tsm.Initiator;
import tfw.tsm.TriggeredCommit;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;

public class TestTriggeredCommit extends TriggeredCommit {
    private final Object lock = new Object();

    private int count = 0;
    private Map<ObjectECD, Object> state = null;

    public TestTriggeredCommit(
            final String name,
            final StatelessTriggerECD statelessTriggerECD,
            final ObjectECD[] nonTriggering,
            Initiator[] initiators) {
        super("TestTriggeredCommit[" + name + "]", statelessTriggerECD, nonTriggering, initiators);
    }

    @Override
    protected void commit() {
        synchronized (lock) {
            count++;
            state = get();
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }

    public Map<ObjectECD, Object> getState() {
        synchronized (lock) {
            return state;
        }
    }

    public static TestTriggeredCommitBuilder builder() {
        return new TestTriggeredCommitBuilder();
    }

    public static class TestTriggeredCommitBuilder {
        private String name = null;
        private StatelessTriggerECD statelessTriggerECD = null;
        private List<ObjectECD> nonTriggeringEcds = new ArrayList<>();
        private List<Initiator> initiators = new ArrayList<>();

        TestTriggeredCommitBuilder() {}

        public TestTriggeredCommitBuilder setName(final String name) {
            this.name = name;

            return this;
        }

        public TestTriggeredCommitBuilder setTriggeringEcd(final StatelessTriggerECD statelessTriggerECD) {
            this.statelessTriggerECD = statelessTriggerECD;

            return this;
        }

        public TestTriggeredCommitBuilder addNonTriggeringEcd(final ObjectECD objectECD) {
            nonTriggeringEcds.add(objectECD);

            return this;
        }

        public TestTriggeredCommitBuilder addInitiator(final Initiator initiator) {
            initiators.add(initiator);

            return this;
        }

        public TestTriggeredCommit build() {
            Argument.assertNotNull(name, "name");
            Argument.assertNotNull(statelessTriggerECD, "statelessTriggerECD");

            final ObjectECD[] nte = nonTriggeringEcds.toArray(new ObjectECD[nonTriggeringEcds.size()]);
            final Initiator[] i = initiators.toArray(new Initiator[initiators.size()]);

            return new TestTriggeredCommit(name, statelessTriggerECD, nte, i);
        }
    }
}
