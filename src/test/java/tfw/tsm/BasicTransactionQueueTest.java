package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 */
class BasicTransactionQueueTest {
    @Test
    void testAdd() {
        BasicTransactionQueue queue = new BasicTransactionQueue();

        try {
            queue.invokeLater(null);
            fail("add() accepted null runnable");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
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
    void testAddNWait() {
        BasicTransactionQueue queue = new BasicTransactionQueue();
        TestRunnable ts = new TestRunnable(500);

        queue.invokeLater(ts);
        queue.waitTilEmpty();

        assertTrue(ts.done, "Not done after waitTilEmpty()");
    }

    @Test
    void testInvokeAndWait() throws Exception {
        BasicTransactionQueue queue = new BasicTransactionQueue();
        try {
            queue.invokeAndWait(null);
            fail("invokeAndWait() accepted null");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        TestRunnable tr1 = new TestRunnable(20);
        TestRunnable tr2 = new TestRunnable(0);
        TestRunnable tr3 = new TestRunnable(5);
        queue.invokeLater(tr1);
        queue.invokeAndWait(tr2);
        assertEquals(2, tr2.cnt, "tr2.cnt");
        queue.invokeLater(tr3);
        queue.waitTilEmpty();

        assertEquals(1, tr1.cnt, "tr1.cnt");
        assertEquals(2, tr2.cnt, "tr2.cnt");
        assertEquals(3, tr3.cnt, "tr3.cnt");
    }
}
