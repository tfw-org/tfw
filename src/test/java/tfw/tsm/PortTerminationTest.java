package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

/**
 * Test to make sure rooted components are terminated.
 */
class PortTerminationTest {
    static Exception expected = null;

    @Test
    void testUnTerminatedPort() {
        ObjectECD ecd = new StringECD("Test");
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            public void handle(Exception exception) {
                PortTerminationTest.expected = exception;
            }
        });

        // rf.setLogging(true);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("test", queue);

        Commit commit = new Commit("test", new ObjectECD[] {ecd}) {
            public void commit() {}
        };

        root.add(commit);
        queue.waitTilEmpty();

        boolean failed = false;

        if (expected == null) {
            failed = true;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertNotNull(expected, "Root.add() accepted child with un-terminated ports!");
        // assertFalse("waitTilEmpty() failed", failed);
    }
}
