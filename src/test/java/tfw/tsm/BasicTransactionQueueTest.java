package tfw.tsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 *
 */
final class BasicTransactionQueueTest {
    @Test
    void addTest() {
        BasicTransactionQueue queue = new BasicTransactionQueue();

        assertThatThrownBy(() -> queue.invokeLater(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("runnable == null not allowed!");
    }

    private int count = 0;

    private class TestRunnable implements Runnable {

        public boolean done = false;
        public int cnt = -1;
        public final int sleepTime;

        public TestRunnable(int sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepTime);
                done = true;
                this.cnt = ++count;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void addNWaitTest() {
        BasicTransactionQueue queue = new BasicTransactionQueue();
        TestRunnable ts = new TestRunnable(500);

        queue.invokeLater(ts);
        queue.waitTilEmpty();

        assertThat(ts.done).isTrue();
    }

    @Test
    void invokeAndWaitTest() throws Exception {
        BasicTransactionQueue queue = new BasicTransactionQueue();

        assertThatThrownBy(() -> queue.invokeAndWait(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("runnable == null not allowed!");

        TestRunnable tr1 = new TestRunnable(20);
        TestRunnable tr2 = new TestRunnable(0);
        TestRunnable tr3 = new TestRunnable(5);

        queue.invokeLater(tr1);
        queue.invokeAndWait(tr2);

        assertThat(tr2.cnt).isEqualTo(2);

        queue.invokeLater(tr3);
        queue.waitTilEmpty();

        assertThat(tr1.cnt).isEqualTo(1);
        assertThat(tr2.cnt).isEqualTo(2);
        assertThat(tr3.cnt).isEqualTo(3);
    }
}
