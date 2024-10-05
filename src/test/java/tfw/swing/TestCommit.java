package tfw.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tfw.check.Argument;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class TestCommit extends Commit {
    public interface Builder {
        Builder setName(final String name);

        Builder addTriggeringEcd(final ObjectECD triggeringEcd);

        Builder addNonTriggeringEcd(final ObjectECD nonTriggeringEcd);

        Builder addInitiator(final Initiator initiator);

        TestCommit create();
    }

    public int count = 0;
    public Map<ObjectECD, Object> state = null;

    public TestCommit(
            final String name,
            final ObjectECD[] triggeringEcds,
            final ObjectECD[] nonTriggeringEcds,
            Initiator[] initiators) {
        super("TestCommit[" + name + "]", triggeringEcds, nonTriggeringEcds, initiators);
    }

    @Override
    protected void commit() {
        count++;
        state = get();
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    private static final class BuilderImpl implements Builder {
        private String name = null;
        private List<ObjectECD> triggeringEcds = new ArrayList<>();
        private List<ObjectECD> nonTriggeringEcds = new ArrayList<>();
        private List<Initiator> initiators = new ArrayList<>();

        @Override
        public Builder setName(final String name) {
            this.name = name;

            return this;
        }

        @Override
        public Builder addTriggeringEcd(final ObjectECD triggeringEcd) {
            triggeringEcds.add(triggeringEcd);

            return this;
        }

        @Override
        public Builder addNonTriggeringEcd(final ObjectECD nonTriggeringEcd) {
            nonTriggeringEcds.add(nonTriggeringEcd);

            return this;
        }

        @Override
        public Builder addInitiator(final Initiator initiator) {
            initiators.add(initiator);

            return this;
        }

        @Override
        public TestCommit create() {
            Argument.assertNotNull(name, "name");
            Argument.assertGreaterThan(triggeringEcds.size(), 0, "triggeringEcds.size()");

            final ObjectECD[] te = triggeringEcds.toArray(new ObjectECD[triggeringEcds.size()]);
            final ObjectECD[] nte = nonTriggeringEcds.toArray(new ObjectECD[nonTriggeringEcds.size()]);
            final Initiator[] i = initiators.toArray(new Initiator[initiators.size()]);

            return new TestCommit(name, te, nte, i);
        }
    }
}
