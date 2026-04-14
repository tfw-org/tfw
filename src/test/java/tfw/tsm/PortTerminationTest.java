package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

final class PortTerminationTest {
    static Exception expected = null;

    @Test
    void unTerminatedPortTest() {
        final ObjectECD ecd = new StringECD("Test");
        final BasicTransactionQueue queue = new BasicTransactionQueue();
        final Root root = Root.builder()
                .setName("test")
                .setTransactionQueue(queue)
                .setTransactionExceptionHandler(new TransactionExceptionHandler() {
                    @Override
                    public void handle(Exception exception) {
                        PortTerminationTest.expected = exception;
                    }
                })
                .build();

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
