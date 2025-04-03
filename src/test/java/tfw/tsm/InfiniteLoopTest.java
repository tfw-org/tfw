package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class InfiniteLoopTest {
    private static final StringECD PORT_A = new StringECD("a");
    private static final StringECD PORT_B = new StringECD("b");
    private static final StringECD PORT_C = new StringECD("c");
    String cvalue = null;
    private boolean loop = true;
    private boolean transactionStarted = false;
    private final Converter trigger = new Converter("TriggeredConverter", new StringECD[] {PORT_C}, null, null) {
        @Override
        protected void convert() {
            cvalue = (String) get(PORT_C);
        }
    };

    private Converter convertAtoB =
            new Converter("A to B Converter", new ObjectECD[] {PORT_A}, new ObjectECD[] {PORT_B}) {
                @Override
                protected void convert() {
                    transactionStarted = true;
                    if (loop) {
                        set(PORT_B, "from a to b");
                    }
                }
            };

    private Converter convertBtoA =
            new Converter("B to A Converter", new ObjectECD[] {PORT_B}, new ObjectECD[] {PORT_A}) {
                @Override
                protected void convert() {
                    if (loop) {
                        set(PORT_A, "from b to a");
                    }
                }
            };

    private Initiator initiator = new Initiator("Infinite loop initiator", new ObjectECD[] {PORT_A, PORT_C});
    public boolean isCommit = false;
    boolean isDebugCommit = false;
    String avalue = null;
    String bvalue = null;
    private final Commit commit = new Commit("Infinite loop commit", new ObjectECD[] {PORT_A, PORT_B}) {
        @Override
        protected void commit() {
            isCommit = true;
            avalue = (String) get(PORT_A);
            bvalue = (String) get(PORT_B);
        }

        @Override
        protected void debugCommit() {
            isDebugCommit = true;
            avalue = (String) get(PORT_A);
            bvalue = (String) get(PORT_B);
        }
    };

    @Test
    void infiniteLoopTest() throws Exception {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        rf.addEventChannel(PORT_A, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(PORT_B, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(PORT_C, null, AlwaysChangeRule.RULE, null);

        Root root = rf.create("Infinite loop test", queue);
        root.add(initiator);
        root.add(convertAtoB);

        root.add(convertBtoA);
        root.add(trigger);
        root.add(commit);
        initiator.set(PORT_A, "kick off value");
        assertThat(isCommit).isFalse();
        assertThat(isDebugCommit).isFalse();

        String newTransaction = "generating new transaction";

        while (transactionStarted == false) {
            Thread.sleep(10);
        }

        initiator.set(PORT_C, newTransaction);

        // Give it a chance to break through
        // Note that there is no guarantee that we
        // sleep long enough...
        Thread.sleep(50);

        assertThat(cvalue).isNull();

        // terminate the infinite loop transaction...
        loop = false;

        // Give the follow on transaction a chance to
        // to execute...
        queue.waitTilEmpty();
        assertThat(newTransaction).isEqualTo(cvalue);
    }
}
