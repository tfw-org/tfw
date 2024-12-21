package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import org.junit.jupiter.api.Test;
import tfw.swing.SwingTestUtil;
import tfw.swing.SwingUtil;
import tfw.swing.TestCommit;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.OneDeepStateQueueFactory;
import tfw.tsm.Root;
import tfw.tsm.ecd.StringECD;

class DocumentListenerFactoryTest {
    private static final String ROOT_NAME = "Root";
    private static final String STRING_ONE = "AAAAAAAAAAAA";
    private static final String STRING_ONE_AND_TWO = "AAAAAAAAA";
    private static final String TEST_NAME = "TestName";
    private static final String TEST_COMMIT_NAME = "TestCommit";
    private static final StringECD TEXT_ECD = new StringECD("TestText");

    @Test
    void testArguments() {
        final OneDeepStateQueueFactory stateQueueFactory = new OneDeepStateQueueFactory();

        assertThrows(IllegalArgumentException.class, () -> DocumentListenerFactory.create(null, TEXT_ECD, null));
        assertThrows(
                IllegalArgumentException.class,
                () -> DocumentListenerFactory.create(null, TEXT_ECD, stateQueueFactory));
        assertThrows(IllegalArgumentException.class, () -> DocumentListenerFactory.create(TEST_NAME, null, null));
        assertThrows(
                IllegalArgumentException.class,
                () -> DocumentListenerFactory.create(TEST_NAME, null, stateQueueFactory));
    }

    @Test
    void testStateQueueFactoryNull() throws Exception {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final Root root = Root.builder()
                .setName(ROOT_NAME)
                .addEventChannel(TEXT_ECD, null)
                .setTransactionQueue(basicTransactionQueue)
                .build();
        final DocumentListener documentListener = DocumentListenerFactory.create(TEST_NAME, TEXT_ECD, null);
        final TestCommit testCommit = TestCommit.builder()
                .setName(TEST_COMMIT_NAME)
                .addTriggeringEcd(TEXT_ECD)
                .build();
        final TestDocument testDocument = new TestDocument();

        SwingUtil.addObjectToBranch(documentListener, root);
        root.add(testCommit);

        testDocument.addDocumentListener(documentListener);
        testDocument.insertString(0, STRING_ONE, null);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertEquals(STRING_ONE, testCommit.getState().get(TEXT_ECD));

        testDocument.remove(STRING_ONE.length() / 4 * 3, STRING_ONE.length() / 4);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertEquals(STRING_ONE_AND_TWO, testCommit.getState().get(TEXT_ECD));

        testDocument.fireChangedUpdate(STRING_ONE.length() / 3, STRING_ONE.length() / 3);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertEquals(STRING_ONE_AND_TWO, testCommit.getState().get(TEXT_ECD));

        final ExceptionDocument exceptionDocument = new ExceptionDocument();

        exceptionDocument.addDocumentListener(documentListener);
        exceptionDocument.insertString(0, STRING_ONE, null);
    }

    private static class TestDocument extends PlainDocument {
        public void fireChangedUpdate(final int offset, final int length) {
            fireChangedUpdate(new DefaultDocumentEvent(offset, length, EventType.CHANGE));
        }
    }

    private static class ExceptionDocument extends PlainDocument {
        @Override
        public String getText(int offset, int length) throws BadLocationException {
            throw new BadLocationException("Test Exception Always Thrown!", 0);
        }
    }
}
