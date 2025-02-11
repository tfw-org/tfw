package tfw.swing.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.event.ActionListener;
import org.junit.jupiter.api.Test;
import tfw.swing.SwingTestUtil;
import tfw.swing.SwingUtil;
import tfw.swing.TestTriggeredCommit;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.OneDeepStateQueueFactory;
import tfw.tsm.Root;
import tfw.tsm.ecd.StatelessTriggerECD;

final class ActionListenerFactoryTest {
    private static final String ROOT_NAME = "Root";
    private static final String TEST_NAME = "TestName";
    private static final String TEST_TRIGGERED_COMMIT_NAME = "TestTriggeredCommit";
    private static final StatelessTriggerECD STATELESS_TRIGGER_ECD = new StatelessTriggerECD("TestTrigger");

    @Test
    void argumentsTest() {
        final OneDeepStateQueueFactory stateQueueFactory = new OneDeepStateQueueFactory();

        assertThatThrownBy(() -> ActionListenerFactory.create(null, STATELESS_TRIGGER_ECD, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> ActionListenerFactory.create(null, STATELESS_TRIGGER_ECD, stateQueueFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name == null not allowed!");
        assertThatThrownBy(() -> ActionListenerFactory.create(TEST_NAME, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0]== null not allowed!");
        assertThatThrownBy(() -> ActionListenerFactory.create(TEST_NAME, null, stateQueueFactory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sources[0]== null not allowed!");
    }

    @Test
    void stateQueueFactoryNullTest() throws Exception {
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

        assertThat(testTriggeredCommit.getCount()).isEqualTo(1);
    }

    @Test
    void stateQueueFactoryNonNullTest() throws Exception {
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

        assertThat(testTriggeredCommit.getCount()).isEqualTo(1);
    }
}
