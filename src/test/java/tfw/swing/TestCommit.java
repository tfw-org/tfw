package tfw.swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class TestCommit extends Commit {
    private final Object lock = new Object();

    private int count = 0;
    private Map<ObjectECD, Object> state = null;

    public TestCommit(
            final String name,
            final ObjectECD[] triggeringEcds,
            final ObjectECD[] nonTriggeringEcds,
            Initiator[] initiators) {
        super("TestCommit[" + name + "]", triggeringEcds, nonTriggeringEcds, initiators);
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
            return new HashMap<>(state);
        }
    }

    public static TestCommitBuilder builder() {
        return new TestCommitBuilder();
    }

    public static final class TestCommitBuilder {
        private String name = null;
        private List<ObjectECD> triggeringEcds = new ArrayList<>();
        private List<ObjectECD> nonTriggeringEcds = new ArrayList<>();
        private List<Initiator> initiators = new ArrayList<>();

        TestCommitBuilder() {}

        public TestCommitBuilder setName(final String name) {
            this.name = name;

            return this;
        }

        public TestCommitBuilder addTriggeringEcd(final ObjectECD triggeringEcd) {
            triggeringEcds.add(triggeringEcd);

            return this;
        }

        public TestCommitBuilder addNonTriggeringEcd(final ObjectECD nonTriggeringEcd) {
            nonTriggeringEcds.add(nonTriggeringEcd);

            return this;
        }

        public TestCommitBuilder addInitiator(final Initiator initiator) {
            initiators.add(initiator);

            return this;
        }

        public TestCommit build() {
            Argument.assertNotNull(name, "name");
            Argument.assertGreaterThan(triggeringEcds.size(), 0, "triggeringEcds.size()");

            final ObjectECD[] te = triggeringEcds.toArray(new ObjectECD[triggeringEcds.size()]);
            final ObjectECD[] nte = nonTriggeringEcds.toArray(new ObjectECD[nonTriggeringEcds.size()]);
            final Initiator[] i = initiators.toArray(new Initiator[initiators.size()]);

            return new TestCommit(name, te, nte, i);
        }
    }
}
