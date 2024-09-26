package tfw.tsm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import tfw.tsm.ecd.IntegerECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StringECD;

class TranslatorTest {
    private final String answer = "Hello World";

    private String result = null;

    private ObjectECD portA = new StringECD("a");

    private ObjectECD portB = new StringECD("b");

    private ObjectECD[] eventChannels = new ObjectECD[] {portA};

    private Initiator initiator = new Initiator("Initiator", eventChannels);

    private Commit commit = new Commit("Commit", eventChannels) {
        protected void commit() {
            result = (String) get(portA);
        }
    };

    @Test
    void testTranslation() throws InterruptedException, InvocationTargetException {
        RootFactory rf = new RootFactory();
        rf.addEventChannel(portB, null, AlwaysChangeRule.RULE, null);

        TestExceptionHandler handler = new TestExceptionHandler();
        rf.setTransactionExceptionHandler(handler);

        BasicTransactionQueue queue = new BasicTransactionQueue();
        Root topBranch = rf.create("Top Branch", queue);

        BranchFactory bf = new BranchFactory();
        bf.addTranslation(portA, portB);

        Branch middleBranch1 = bf.create("Middle Branch commit");
        middleBranch1.add(commit);

        bf.clear();
        bf.addTranslation(portA, portB);

        Branch middleBranch2 = bf.create("Middle Branch initiator");
        middleBranch2.add(initiator);

        topBranch.add(middleBranch1);
        topBranch.add(middleBranch2);

        // Visualize.print(topBranch);
        initiator.set(portA, answer);
        queue.waitTilEmpty();
        assertEquals(answer, result, "initial connections");

        String newAnswer = "Good bye world";
        initiator.set(portA, newAnswer);
        queue.waitTilEmpty();
        assertEquals(newAnswer, result, "initial connections");

        middleBranch1.remove(commit);
        result = null;
        initiator.set(portA, answer);
        queue.waitTilEmpty();
        assertEquals(null, result, "disconnect sink");

        result = null;
        middleBranch1.add(commit);
        queue.waitTilEmpty();
        checkHandler(handler);

        // No need to send...should fire on connect.
        // initiator.set("a", answer);
        assertEquals(answer, result, "fire on sink reconnect");

        middleBranch2.remove(initiator);
        queue.waitTilEmpty();
        result = null;

        // Fire source unconnected.
        initiator.set(portA, answer);
        queue.waitTilEmpty();

        assertEquals(null, result, "source removal");

        //        result = null;
        //        handler.exception = null;
        //        middleBranch2.add(initiator);
        //        queue.waitTilEmpty();
        //        assertEquals("source fire on reconnect", answer, result);
        //
        //        checkHandler(handler);
    }

    public void testIncompatableTranslation() {
        BranchFactory bf = new BranchFactory();
        StringECD stringECD = new StringECD("StringECD");
        IntegerECD integerECD1 = new IntegerECD("integerECD1", 1, 5);
        IntegerECD integerECD2 = new IntegerECD("integerECD2", 0, 6);
        try {
            bf.addTranslation(integerECD1, integerECD2);
            fail("addTranslation() accepted incompatible ecds");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
        try {
            bf.addTranslation(integerECD2, integerECD1);
            fail("addTranslation() accepted incompatible ecds");
        } catch (IllegalArgumentException expected) {
            // System.out.println(expected);
        }
    }

    private void checkHandler(TestExceptionHandler handler) {
        String message = "No exception";

        if (handler.exception != null) {
            handler.exception.printStackTrace();
            message = handler.exception.getMessage();
        }

        assertNull(handler.exception, "Exception - " + message);
    }

    private static class TestExceptionHandler implements TransactionExceptionHandler {
        Exception exception = null;

        public void handle(Exception exception) {
            // exception.printStackTrace();
            this.exception = exception;
        }
    }
}
