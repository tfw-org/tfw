package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

/**
 *
 */
class InfiniteLoopTest {
    private static int count = 0;
    private static final StringECD porta = new StringECD("a");
    private static final StringECD portb = new StringECD("b");
    private static final StringECD portc = new StringECD("c");
    String cvalue = null;
    private boolean loop = true;
    private boolean transactionStarted = false;
    private final Converter trigger = new Converter("TriggeredConverter", new StringECD[] {portc}, null, null) {
        protected void convert() {
            cvalue = (String) get(portc);
        }
    };

    private Converter convertAtoB =
            new Converter("A to B Converter", new ObjectECD[] {porta}, new ObjectECD[] {portb}) {
                protected void convert() {
                    transactionStarted = true;
                    // System.out.println("a to b " + count++);

                    if (loop) {
                        set(portb, "from a to b");
                    }
                }
            };

    private Converter convertBtoA =
            new Converter("B to A Converter", new ObjectECD[] {portb}, new ObjectECD[] {porta}) {
                protected void convert() {
                    // System.out.println("b to a " + count++);

                    if (loop) {
                        set(porta, "from b to a");
                    }
                }
            };

    private Initiator initiator = new Initiator("Infinite loop initiator", new ObjectECD[] {porta, portc});
    public boolean isCommit = false;
    boolean isDebugCommit = false;
    String avalue = null;
    String bvalue = null;
    private final Commit commit = new Commit("Infinite loop commit", new ObjectECD[] {porta, portb}) {
        protected void commit() {
            isCommit = true;
            avalue = (String) get(porta);
            bvalue = (String) get(portb);
        }

        protected void debugCommit() {
            isDebugCommit = true;
            avalue = (String) get(porta);
            bvalue = (String) get(portb);
        }
    };

    @Test
    void testInfiniteLoop() throws Exception {
        RootFactory rf = new RootFactory();
        BasicTransactionQueue queue = new BasicTransactionQueue();
        rf.addEventChannel(porta, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(portb, null, AlwaysChangeRule.RULE, null);
        rf.addEventChannel(portc, null, AlwaysChangeRule.RULE, null);

        Root root = rf.create("Infinite loop test", queue);
        root.add(initiator);
        root.add(convertAtoB);

        root.add(convertBtoA);
        root.add(trigger);
        root.add(commit);
        initiator.set(porta, "kick off value");
        // queue.waitTilEmpty();
        assertFalse(isCommit, "commit was called");
        assertFalse(isDebugCommit, "debugCommit was called");

        String newTransaction = "generating new transaction";

        while (transactionStarted == false) {
            Thread.sleep(10);
        }

        initiator.set(portc, newTransaction);

        // Give it a chance to break through
        // Note that there is no guarantee that we
        // sleep long enough...
        Thread.sleep(50);

        assertNull(cvalue, "initiator broke into the infinite transaction.");

        // terminate the infinite loop transaction...
        loop = false;

        // Give the follow on transaction a chance to
        // to execute...
        queue.waitTilEmpty();
        assertEquals(newTransaction, cvalue, "intiator did cause value to set after loop was broken");
    }
}
