package tfw.swing;

import java.util.ArrayList;
import java.util.List;
import tfw.check.Argument;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public final class TestCommitBuilder {
    private String name = null;
    private List<ObjectECD> triggeringEcds = new ArrayList<>();
    private List<ObjectECD> nonTriggeringEcds = new ArrayList<>();
    private List<Initiator> initiators = new ArrayList<>();

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
