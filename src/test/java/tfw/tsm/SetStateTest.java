package tfw.tsm;

import junit.framework.TestCase;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Converter;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.RootFactory;
import tfw.tsm.ecd.StringECD;

public class SetStateTest extends TestCase
{
    /**
     * Verifies that an exception is thrown if the component attempts to set an
     * event channel twice in the same state change cyecle.
     */
    public void testDoubleSet()
    {
        RootFactory rf = new RootFactory();
        StringECD ecd = new StringECD("myECD");
        rf.addEventChannel(ecd);
        TestTransactionExceptionHandler exceptionHandler = new TestTransactionExceptionHandler();
        rf.setTransactionExceptionHandler(exceptionHandler);
        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root root = rf.create("Test", queue);
        root.add(new DoubleSetConverter(ecd));
        Initiator initiator = new Initiator("MyInitiator", ecd);
        root.add(initiator);
        initiator.set(ecd, "hello");
        queue.waitTilEmpty();

        assertNotNull("Double set failed to throw exception",
                exceptionHandler.exp);
    }

    private class DoubleSetConverter extends Converter
    {
        private final StringECD ecd;

        public DoubleSetConverter(StringECD ecd)
        {
            super("DoubleSetConverter", new StringECD[] { ecd },
                    new StringECD[] { ecd });
            this.ecd = ecd;
        }

        protected void convert()
        {
            set(ecd, "value");
            // Attempt to set the same event channel twice...
            set(ecd, "newValue");
        }
    }
}
