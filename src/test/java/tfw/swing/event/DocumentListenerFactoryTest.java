package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

final class DocumentListenerFactoryTest {
    private static final String ROOT_NAME = "Root";
    private static final String STRING_ONE = "AAAAAAAAAAAA";
    private static final String STRING_ONE_AND_TWO = "AAAAAAAAA";
    private static final String TEST_NAME = "TestName";
    private static final String TEST_COMMIT_NAME = "TestCommit";
    private static final StringECD TEXT_ECD = new StringECD("TestText");

    @Test
    void argumentsTest() {
        final OneDeepStateQueueFactory stateQueueFactory = new OneDeepStateQueueFactory();

        assertThatThrownBy(() -> DocumentListenerFactory.create(null, TEXT_ECD, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> DocumentListenerFactory.create(null, TEXT_ECD, stateQueueFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> DocumentListenerFactory.create(TEST_NAME, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0]== null not allowed!");
        assertThatThrownBy(() -> DocumentListenerFactory.create(TEST_NAME, null, stateQueueFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0]== null not allowed!");
    }

    @Test
    void stateQueueFactoryNullTest() throws Exception {
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

        assertThat(testCommit.getState()).containsEntry(TEXT_ECD, STRING_ONE);

        testDocument.remove(STRING_ONE.length() / 4 * 3, STRING_ONE.length() / 4);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertThat(testCommit.getState()).containsEntry(TEXT_ECD, STRING_ONE_AND_TWO);

        testDocument.fireChangedUpdate(STRING_ONE.length() / 3, STRING_ONE.length() / 3);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertThat(testCommit.getState()).containsEntry(TEXT_ECD, STRING_ONE_AND_TWO);

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
