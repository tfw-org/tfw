package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.event.ActionListener;
import org.junit.jupiter.api.Test;
import tfw.swing.SwingTestUtil;
import tfw.swing.SwingUtil;
import tfw.swing.TestTriggeredCommit;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.OneDeepStateQueueFactory;
import tfw.tsm.Root;
import tfw.tsm.ecd.StatelessTriggerECD;

class ActionListenerFactoryTest {
    private static final String ROOT_NAME = "Root";
    private static final String TEST_NAME = "TestName";
    private static final String TEST_TRIGGERED_COMMIT_NAME = "TestTriggeredCommit";
    private static final StatelessTriggerECD STATELESS_TRIGGER_ECD = new StatelessTriggerECD("TestTrigger");

    @Test
    void testArguments() {
        final OneDeepStateQueueFactory stateQueueFactory = new OneDeepStateQueueFactory();

        assertThrows(
                IllegalArgumentException.class, () -> ActionListenerFactory.create(null, STATELESS_TRIGGER_ECD, null));
        assertThrows(
                IllegalArgumentException.class,
                () -> ActionListenerFactory.create(null, STATELESS_TRIGGER_ECD, stateQueueFactory));
        assertThrows(IllegalArgumentException.class, () -> ActionListenerFactory.create(TEST_NAME, null, null));
        assertThrows(
                IllegalArgumentException.class, () -> ActionListenerFactory.create(TEST_NAME, null, stateQueueFactory));
    }

    @Test
    void testStateQueueFactoryNull() throws Exception {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final Root root = Root.builder()
                .setName(ROOT_NAME)
                .addEventChannel(STATELESS_TRIGGER_ECD, null)
                .setTransactionQueue(basicTransactionQueue)
                .build();
        final ActionListener actionListener = ActionListenerFactory.create(TEST_NAME, STATELESS_TRIGGER_ECD, null);
        final TestTriggeredCommit testTriggeredCommit = TestTriggeredCommit.builder()
                .setName(TEST_TRIGGERED_COMMIT_NAME)
                .setTriggeringEcd(STATELESS_TRIGGER_ECD)
                .build();

        SwingUtil.addObjectToBranch(actionListener, root);
        root.add(testTriggeredCommit);

        actionListener.actionPerformed(null);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertEquals(1, testTriggeredCommit.getCount());
    }

    @Test
    void testStateQueueFactoryNonNull() throws Exception {
        final OneDeepStateQueueFactory stateQueueFactory = new OneDeepStateQueueFactory();
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final Root root = Root.builder()
                .setName(ROOT_NAME)
                .addEventChannel(STATELESS_TRIGGER_ECD, null)
                .setTransactionQueue(basicTransactionQueue)
                .build();
        final ActionListener actionListener =
                ActionListenerFactory.create(TEST_NAME, STATELESS_TRIGGER_ECD, stateQueueFactory);
        final TestTriggeredCommit testTriggeredCommit = TestTriggeredCommit.builder()
                .setName(TEST_TRIGGERED_COMMIT_NAME)
                .setTriggeringEcd(STATELESS_TRIGGER_ECD)
                .build();

        SwingUtil.addObjectToBranch(actionListener, root);
        root.add(testTriggeredCommit);

        actionListener.actionPerformed(null);

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        assertEquals(1, testTriggeredCommit.getCount());
    }
}
