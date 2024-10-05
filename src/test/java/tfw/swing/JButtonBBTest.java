package tfw.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.awt.ecd.ColorECD;
import tfw.awt.ecd.FontECD;
import tfw.swing.ecd.IconECD;
import tfw.tsm.BasicTransactionQueue;
import tfw.tsm.Branch;
import tfw.tsm.BranchProxy;
import tfw.tsm.Initiator;
import tfw.tsm.Root;
import tfw.tsm.ecd.BooleanECD;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class JButtonBBTest {
    public static final StatelessTriggerECD BUTTON_ACTION_ECD = new StatelessTriggerECD("ButtonAction");
    public static final BooleanECD BUTTON_ENABLED_ECD = new BooleanECD("ButtonEnabled");
    public static final String BUTTON_ENABLED_NAME = "Enabled";
    public static final Color BUTTON_BACKGROUND_DEFAULT = new JButton().getBackground();
    public static final ColorECD BUTTON_BACKGROUND_ECD = new ColorECD("ButtonBackground");
    public static final String BUTTON_BACKGROUND_NAME = "Background";
    public static final Color BUTTON_BACKGROUND_TEST = new Color(255, 254, 253);
    public static final Color BUTTON_FOREGROUND_DEFAULT = new JButton().getForeground();
    public static final ColorECD BUTTON_FOREGROUND_ECD = new ColorECD("ButtonForeground");
    public static final String BUTTON_FOREGROUND_NAME = "Foreground";
    public static final Color BUTTON_FOREGROUND_TEST = new Color(0, 1, 2);
    public static final Font BUTTON_FONT_DEFAULT = new JButton().getFont();
    public static final FontECD BUTTON_FONT_ECD = new FontECD("ButtonFont");
    public static final String BUTTON_FONT_NAME = "Font";
    public static final Font BUTTON_FONT_TEST = new Font("Helvetica", Font.PLAIN, 12);
    public static final String BUTTON_TEXT_DEFAULT = new JButton().getText();
    public static final StringECD BUTTON_TEXT_ECD = new StringECD("ButtonText");
    public static final String BUTTON_TEXT_NAME = "Text";
    public static final String BUTTON_TEXT_TEST = "ButtonText";
    public static final Icon BUTTON_ICON_DEFAULT = UIManager.getIcon("FileView.directoryIcon");
    public static final IconECD BUTTON_ICON_ECD = new IconECD("ButtonIcon");
    public static final String BUTTON_ICON_NAME = "Icon";
    public static final Icon BUTTON_ICON_TEST = UIManager.getIcon("FileView.fileIcon");
    public static final String BUTTON_NAME = "TestButton";
    public static final String EMPTY_BUTTON_NAME = "EmptyTestButton";
    public static final String FRAME_NAME = "Frame";
    public static final String INITIATOR_NAME = "Initiator[" + BUTTON_NAME + "]";

    private Branch branch;
    private JButtonBBTestApplication frame;
    private FrameFixture window;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        branch = new Branch(FRAME_NAME);
        frame = GuiActionRunner.execute(() -> new JButtonBBTestApplication(branch));
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    void testJButtonBB() throws Exception {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final Root root = Root.builder()
                .setName(this.getClass().getSimpleName())
                .addEventChannel(BUTTON_ACTION_ECD, null)
                .addEventChannel(BUTTON_ENABLED_ECD, Boolean.TRUE)
                .addEventChannel(BUTTON_BACKGROUND_ECD, BUTTON_BACKGROUND_DEFAULT)
                .addEventChannel(BUTTON_FOREGROUND_ECD, BUTTON_FOREGROUND_DEFAULT)
                .addEventChannel(BUTTON_FONT_ECD, BUTTON_FONT_DEFAULT)
                .addEventChannel(BUTTON_TEXT_ECD, BUTTON_TEXT_DEFAULT)
                .addEventChannel(BUTTON_ICON_ECD, BUTTON_ICON_DEFAULT)
                .setTransactionQueue(basicTransactionQueue)
                .create();
        final Initiator initiator = Initiator.builder()
                .setName(INITIATOR_NAME)
                .addEventChannelDescription(BUTTON_ENABLED_ECD)
                .addEventChannelDescription(BUTTON_BACKGROUND_ECD)
                .addEventChannelDescription(BUTTON_FOREGROUND_ECD)
                .addEventChannelDescription(BUTTON_FONT_ECD)
                .addEventChannelDescription(BUTTON_TEXT_ECD)
                .addEventChannelDescription(BUTTON_ICON_ECD)
                .create();
        final TestCommit testCommit = TestCommit.builder()
                .setName(BUTTON_NAME)
                .addTriggeringEcd(BUTTON_ENABLED_ECD)
                .addTriggeringEcd(BUTTON_BACKGROUND_ECD)
                .addTriggeringEcd(BUTTON_FOREGROUND_ECD)
                .addTriggeringEcd(BUTTON_FONT_ECD)
                .addTriggeringEcd(BUTTON_TEXT_ECD)
                .addTriggeringEcd(BUTTON_ICON_ECD)
                .create();
        final TestTriggeredCommit testTriggeredCommit = TestTriggeredCommit.builder()
                .setName(BUTTON_NAME)
                .setTriggeringEcd(BUTTON_ACTION_ECD)
                .create();

        root.add(branch);
        root.add(initiator);
        root.add(testCommit);
        root.add(testTriggeredCommit);
        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        final JButtonBB emptyButton =
                (JButtonBB) window.button(EMPTY_BUTTON_NAME).target();
        assertEquals(1, new BranchProxy(emptyButton.getBranch()).getChildProxies().length);

        final TestActionListenerBranchBox testActionListenerBranchBox = new TestActionListenerBranchBox();
        emptyButton.addActionListenerToBoth(testActionListenerBranchBox);
        emptyButton.removeActionListenerFromBoth(testActionListenerBranchBox);
        final TestActionListenerTreeComponent testActionListenerTreeComponent =
                new TestActionListenerTreeComponent(BUTTON_ACTION_ECD);
        emptyButton.addActionListenerToBoth(testActionListenerTreeComponent);
        emptyButton.removeActionListenerFromBoth(testActionListenerTreeComponent);
        final TestActionListener testActionListener = new TestActionListener();
        assertThrows(IllegalArgumentException.class, () -> emptyButton.addActionListenerToBoth(testActionListener));
        assertThrows(
                IllegalArgumentException.class, () -> emptyButton.removeActionListenerFromBoth(testActionListener));

        assertEquals(0, testTriggeredCommit.count);

        window.button(BUTTON_NAME).click();

        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.count);

        initiator.set(BUTTON_BACKGROUND_ECD, BUTTON_BACKGROUND_TEST);
        check(BUTTON_BACKGROUND_NAME, basicTransactionQueue, window, testCommit);

        initiator.set(BUTTON_FOREGROUND_ECD, BUTTON_FOREGROUND_TEST);
        check(BUTTON_FOREGROUND_NAME, basicTransactionQueue, window, testCommit);

        initiator.set(BUTTON_ENABLED_ECD, Boolean.FALSE);
        check(BUTTON_ENABLED_NAME, basicTransactionQueue, window, testCommit);

        initiator.set(BUTTON_FONT_ECD, BUTTON_FONT_TEST);
        check(BUTTON_FONT_NAME, basicTransactionQueue, window, testCommit);

        initiator.set(BUTTON_TEXT_ECD, BUTTON_TEXT_TEST);
        check(BUTTON_TEXT_NAME, basicTransactionQueue, window, testCommit);

        initiator.set(BUTTON_ICON_ECD, BUTTON_ICON_TEST);
        check(BUTTON_ICON_NAME, basicTransactionQueue, window, testCommit);
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    private static void check(
            final String testDescription,
            final BasicTransactionQueue basicTransactionQueue,
            final FrameFixture window,
            final TestCommit testCommit)
            throws InvocationTargetException, InterruptedException {
        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        final JButtonFixture jButtonFixture = window.button(BUTTON_NAME);
        final Map<ObjectECD, Object> tfwState = testCommit.state;

        assertEquals(jButtonFixture.isEnabled(), tfwState.get(BUTTON_ENABLED_ECD), testDescription);
        assertEquals(jButtonFixture.background().target(), tfwState.get(BUTTON_BACKGROUND_ECD), testDescription);
        assertEquals(jButtonFixture.foreground().target(), tfwState.get(BUTTON_FOREGROUND_ECD), testDescription);
        assertEquals(jButtonFixture.font().target(), tfwState.get(BUTTON_FONT_ECD), testDescription);
        assertEquals(jButtonFixture.text(), tfwState.get(BUTTON_TEXT_ECD), testDescription);
        assertEquals(jButtonFixture.target().getIcon(), tfwState.get(BUTTON_ICON_ECD), testDescription);
    }
}
