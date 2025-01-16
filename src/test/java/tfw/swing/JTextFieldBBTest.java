package tfw.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchProxy;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

final class JTextFieldBBTest {
    public static final StatelessTriggerECD TEXTFIELD_ACTION_ECD = new StatelessTriggerECD("TextfieldAction");
    public static final BooleanECD TEXTFIELD_ENABLED_ECD = new BooleanECD("TextFieldEnabled");
    public static final String TEXTFIELD_TEXT_DEFAULT = "";
    public static final StringECD TEXTFIELD_TEXT_ECD = new StringECD("TextFieldText");
    public static final String TEXTFIELD_NAME = "TestTextField";
    public static final String FRAME_NAME = "Frame";
    public static final String INITIATOR_NAME = "Initiator[" + TEXTFIELD_NAME + "]";
    public static final String TEST_WIDGET_TEXT = "WidgetText";
    public static final String TEST_INITIATOR_TEXT = "InitiatorText";

    private Branch branch;
    private JTextFieldBBTestApplication frame;
    private FrameFixture window;

    @BeforeAll
    static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void setUp() {
        branch = new Branch(FRAME_NAME);
        frame = GuiActionRunner.execute(() -> new JTextFieldBBTestApplication(branch));
        window = new FrameFixture(BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock(), frame);
        window.show();
    }

    @Test
    void jTextFieldBBTest() throws Exception {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final JTextComponentFixture textField = window.textBox(TEXTFIELD_NAME);
        final Root root = Root.builder()
                .setName(this.getClass().getSimpleName())
                .setLogging(true)
                .addEventChannel(TEXTFIELD_ENABLED_ECD, textField.isEnabled())
                .addEventChannel(TEXTFIELD_TEXT_ECD, textField.text())
                .setTransactionQueue(basicTransactionQueue)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName(INITIATOR_NAME)
                .addEventChannelDescription(TEXTFIELD_ENABLED_ECD)
                .addEventChannelDescription(TEXTFIELD_TEXT_ECD)
                .build();
        final TestCommit testCommit = TestCommit.builder()
                .setName(TEXTFIELD_NAME)
                .addTriggeringEcd(TEXTFIELD_ENABLED_ECD)
                .addTriggeringEcd(TEXTFIELD_TEXT_ECD)
                .build();

        root.add(branch);
        root.add(initiator);
        root.add(testCommit);

        compareWidgetTfwState(textField, testCommit, 1, basicTransactionQueue);

        textField.setText(TEST_WIDGET_TEXT);

        compareWidgetTfwState(textField, testCommit, 2, basicTransactionQueue);

        initiator.set(TEXTFIELD_TEXT_ECD, TEST_INITIATOR_TEXT);

        compareWidgetTfwState(textField, testCommit, 5, basicTransactionQueue);

        initiator.set(TEXTFIELD_ENABLED_ECD, Boolean.FALSE);

        compareWidgetTfwState(textField, testCommit, 6, basicTransactionQueue);
    }

    @Test
    void actionListenerTest() {
        final JTextFieldBB jTextFieldBB = GuiActionRunner.execute(
                () -> JTextFieldBB.builder().setName(TEXTFIELD_NAME).build());

        assertThat(0).isEqualTo(new BranchProxy(jTextFieldBB.getBranch()).getChildProxies().length);

        final TestActionListenerBranchBox testActionListenerBranchBox = new TestActionListenerBranchBox();

        GuiActionRunner.execute(() -> jTextFieldBB.addActionListenerToBoth(testActionListenerBranchBox));
        GuiActionRunner.execute(() -> jTextFieldBB.removeActionListenerFromBoth(testActionListenerBranchBox));

        final TestActionListenerTreeComponent testActionListenerTreeComponent =
                new TestActionListenerTreeComponent(TEXTFIELD_ACTION_ECD);

        GuiActionRunner.execute(() -> jTextFieldBB.addActionListenerToBoth(testActionListenerTreeComponent));
        GuiActionRunner.execute(() -> jTextFieldBB.removeActionListenerFromBoth(testActionListenerTreeComponent));

        final TestActionListener testActionListener = new TestActionListener();

        assertThatThrownBy(
                        () -> GuiActionRunner.execute(() -> jTextFieldBB.addActionListenerToBoth(testActionListener)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("object != (BranchBox || TreeComponent) not allowed!");

        assertThatThrownBy(() ->
                        GuiActionRunner.execute(() -> jTextFieldBB.removeActionListenerFromBoth(testActionListener)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("object != (BranchBox || TreeComponent) not allowed!");
    }

    @Test
    void documentListenerTest() {
        final JTextFieldBB jTextFieldBB = GuiActionRunner.execute(
                () -> JTextFieldBB.builder().setName(TEXTFIELD_NAME).build());

        assertThat(0).isEqualTo(new BranchProxy(jTextFieldBB.getBranch()).getChildProxies().length);

        final TestDocumentListenerBranchBox testDocumentListenerBranchBox = new TestDocumentListenerBranchBox();

        GuiActionRunner.execute(() -> jTextFieldBB.addDocumentListenerToBoth(testDocumentListenerBranchBox));
        GuiActionRunner.execute(() -> jTextFieldBB.removeDocumentListenerFromBoth(testDocumentListenerBranchBox));

        final TestDocumentListenerTreeComponent testDocumentListenerTreeComponent =
                new TestDocumentListenerTreeComponent(TEXTFIELD_ACTION_ECD);

        GuiActionRunner.execute(() -> jTextFieldBB.addDocumentListenerToBoth(testDocumentListenerTreeComponent));
        GuiActionRunner.execute(() -> jTextFieldBB.removeDocumentListenerFromBoth(testDocumentListenerTreeComponent));

        final TestDocumentListener testDocumentListener = new TestDocumentListener();

        assertThatThrownBy(() ->
                        GuiActionRunner.execute(() -> jTextFieldBB.addDocumentListenerToBoth(testDocumentListener)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("object != (BranchBox || TreeComponent) not allowed!");

        assertThatThrownBy(() -> GuiActionRunner.execute(
                        () -> jTextFieldBB.removeDocumentListenerFromBoth(testDocumentListener)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("object != (BranchBox || TreeComponent) not allowed!");
    }

    private static void compareWidgetTfwState(
            final JTextComponentFixture jTextComponentFixture,
            final TestCommit testCommit,
            final int expectedTestCommitCount,
            final BasicTransactionQueue basicTransactionQueue)
            throws Exception {
        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        final boolean widgetEnabled = jTextComponentFixture.isEnabled();
        final String widgetText = jTextComponentFixture.text();
        final Map<ObjectECD, Object> tfwState = testCommit.getState();
        final boolean tfwEnabled = (Boolean) tfwState.get(TEXTFIELD_ENABLED_ECD);
        final String tfwText = (String) tfwState.get(TEXTFIELD_TEXT_ECD);

        assertThat(expectedTestCommitCount).isEqualTo(testCommit.getCount());

        assertThat(widgetText).isEqualTo(tfwText);
        assertThat(widgetEnabled).isEqualTo(tfwEnabled);
    }
}
