package tfw.swing;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import tfw.tsm.BasicTransactionQueue;

public final class SwingTestUtil {
    private SwingTestUtil() {}

    public static void waitForTfwAndSwing(final BasicTransactionQueue basicTransactionQueue)
            throws InvocationTargetException, InterruptedException {
        basicTransactionQueue.waitTilEmpty();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                // Nothing to do.
            }
        });
    }
}
