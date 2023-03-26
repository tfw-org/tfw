package tfw.tsm;

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Commit;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.TransactionExceptionHandler;
import tfw.tsm.ecd.StringECD;

public class GetPreviousStateTest extends TestCase
{
    StringECD channel = new StringECD("channel");

    public void testIsStateChanged()
    {
        final String initialState = "initialState";
        final String stateChangeOne = "StateOne";
        final String stateChangeTwo = "StateTwo";
        RootFactory rf = new RootFactory();
        rf.setTransactionExceptionHandler(new TransactionExceptionHandler()
        {
            public void handle(Exception e)
            {
                e.printStackTrace();
                fail("Test failed with an exception: " + e.getMessage());
            }
        });
        rf.addEventChannel(channel, initialState);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("TestRoot", queue);

        TestConverter converter = new TestConverter();
        TestCommit commit = new TestCommit();
        root.add(converter);
        root.add(commit);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, initialState, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, initialState, commit.value);

        Initiator initiator = new Initiator("initiator", channel);
        root.add(initiator);
        initiator.set(channel, stateChangeOne);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, initialState, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, initialState, commit.value);
        
        initiator.set(channel, stateChangeTwo);
        queue.waitTilEmpty();
        assertEquals("getPreviousCycleState() failed inital value = "
                + converter.value, stateChangeOne, converter.value);
        assertEquals("getPreviousTransactionState() failed initial value = "
                + commit.value, stateChangeOne, commit.value);
    }

    private class TestConverter extends Converter
    {
        private String value = null;

        public TestConverter()
        {
            super("TestConverter", new StringECD[] { channel },
                    new StringECD[] {});
        }

        protected void convert()
        {
            value = (String) this.getPreviousCycleState(channel);
        }
    }

    private class TestCommit extends Commit
    {
        private String value = null;

        public TestCommit()
        {
            super("TestCommit", new StringECD[] { channel });
        }

        protected void commit()
        {
            value = (String) this.getPreviousTransactionState(channel);
        }
    }
}
