package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class PortTerminationTest {
    static Exception expected = null;

    @Test
    void unTerminatedPortTest() {
        ObjectECD ecd = new StringECD("Test");
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler() {
            @Override
            public void handle(Exception exception) {
                PortTerminationTest.expected = exception;
            }
        });

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("test", queue);

        Commit commit = new Commit("test", new ObjectECD[] {ecd}) {
            @Override
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

        assertThat(expected).isNotNull();
        // assertFalse("waitTilEmpty() failed", failed);
    }
}
