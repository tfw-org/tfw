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
    public interface Builder {
        Builder setName(final String name);

        Builder setTriggeringEcd(final StatelessTriggerECD statelessTriggerECD);

        Builder addNonTriggeringEcd(final ObjectECD objectECD);

        Builder addInitiator(final Initiator initiator);

        TestTriggeredCommit create();
    }

    public int count = 0;
    public Map<ObjectECD, Object> state = null;

    public TestTriggeredCommit(
            final String name,
            final StatelessTriggerECD statelessTriggerECD,
            final ObjectECD[] nonTriggering,
            Initiator[] initiators) {
        super("TestTriggeredCommit[" + name + "]", statelessTriggerECD, nonTriggering, initiators);
    }

    @Override
    protected void commit() {
        count++;
        state = get();
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    private static class BuilderImpl implements Builder {
        private String name = null;
        private StatelessTriggerECD statelessTriggerECD = null;
        private List<ObjectECD> nonTriggeringEcds = new ArrayList<>();
        private List<Initiator> initiators = new ArrayList<>();

        @Override
        public Builder setName(final String name) {
            this.name = name;

            return this;
        }

        @Override
        public Builder setTriggeringEcd(final StatelessTriggerECD statelessTriggerECD) {
            this.statelessTriggerECD = statelessTriggerECD;

            return this;
        }

        @Override
        public Builder addNonTriggeringEcd(final ObjectECD objectECD) {
            nonTriggeringEcds.add(objectECD);

            return this;
        }

        @Override
        public Builder addInitiator(final Initiator initiator) {
            initiators.add(initiator);

            return this;
        }

        @Override
        public TestTriggeredCommit create() {
            Argument.assertNotNull(name, "name");
            Argument.assertNotNull(statelessTriggerECD, "statelessTriggerECD");

            final ObjectECD[] nte = nonTriggeringEcds.toArray(new ObjectECD[nonTriggeringEcds.size()]);
            final Initiator[] i = initiators.toArray(new Initiator[initiators.size()]);

            return new TestTriggeredCommit(name, statelessTriggerECD, nte, i);
        }
    }
}
