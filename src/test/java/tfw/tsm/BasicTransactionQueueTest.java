package tfw.tsm;

import junit.framework.TestCase;

/**
 *
 */
public class BasicTransactionQueueTest extends TestCase {
    public void testAdd() {
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

    public void testAddNWait() {
        BasicTransactionQueue queue = new BasicTransactionQueue();
        TestRunnable ts = new TestRunnable(500);

        queue.invokeLater(ts);
        queue.waitTilEmpty();

        assertTrue("Not done after waitTilEmpty()", ts.done);
    }

    public void testInvokeAndWait() throws Exception {
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
        assertEquals("tr2.cnt", 2, tr2.cnt);
        queue.invokeLater(tr3);
        queue.waitTilEmpty();

        assertEquals("tr1.cnt", 1, tr1.cnt);
        assertEquals("tr2.cnt", 2, tr2.cnt);
        assertEquals("tr3.cnt", 3, tr3.cnt);
    }
}
