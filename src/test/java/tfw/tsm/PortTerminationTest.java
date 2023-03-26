package tfw.tsm;

import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

import junit.framework.TestCase;


/**
 * Test to make sure rooted components are terminated.
 */
public class PortTerminationTest extends TestCase
{
    static Exception expected = null;

    public void testUnTerminatedPort()
    {
        ObjectECD ecd = new StringECD("Test");
        RootFactory rf = new RootFactory();
		rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
			{
				public void handle(Exception exception)
				{
					PortTerminationTest.expected = exception;
				}
			});

        //rf.setLogging(true);
        BasicTransactionQueue queue = new BasicTransactionQueue();
		Root root = rf.create("test", queue);

        Commit commit = new Commit("test", new ObjectECD[]{ ecd })
            {
                public void commit()
                {
                }
            };

        root.add(commit);
        queue.waitTilEmpty();

        boolean failed = false;

        if (expected == null)
        {
            failed = true;

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        assertNotNull("Root.add() accepted child with un-terminated ports!",
            expected);
        //assertFalse("waitTilEmpty() failed", failed);
    }
}
