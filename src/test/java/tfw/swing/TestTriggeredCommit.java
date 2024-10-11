package tfw.swing;

import java.util.Map;
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
}
