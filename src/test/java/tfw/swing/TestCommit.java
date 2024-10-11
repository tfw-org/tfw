package tfw.swing;

import java.util.Map;
import tfw.tsm.Commit;
import tfw.tsm.Initiator;
import tfw.tsm.ecd.ObjectECD;

public class TestCommit extends Commit {
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

    public static TestCommitBuilder builder() {
        return new TestCommitBuilder();
    }
}
