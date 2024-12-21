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
import org.assertj.swing.core.BasicRobot;
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
    public static final ColorECD BUTTON_BACKGROUND_ECD = new ColorECD("ButtonBackground");
    public static final String BUTTON_BACKGROUND_NAME = "Background";
    public static final Color BUTTON_BACKGROUND_TEST = new Color(255, 254, 253);
    public static final ColorECD BUTTON_FOREGROUND_ECD = new ColorECD("ButtonForeground");
    public static final String BUTTON_FOREGROUND_NAME = "Foreground";
    public static final Color BUTTON_FOREGROUND_TEST = new Color(0, 1, 2);
    public static final FontECD BUTTON_FONT_ECD = new FontECD("ButtonFont");
    public static final String BUTTON_FONT_NAME = "Font";
    public static final Font BUTTON_FONT_TEST = new Font("Helvetica", Font.PLAIN, 12);
    public static final StringECD BUTTON_TEXT_ECD = new StringECD("ButtonText");
    public static final String BUTTON_TEXT_NAME = "Text";
    public static final String BUTTON_TEXT_TEST = "ButtonText";
    public static final Icon BUTTON_ICON_DEFAULT = UIManager.getIcon("FileView.directoryIcon");
    public static final IconECD BUTTON_ICON_ECD = new IconECD("ButtonIcon");
    public static final String BUTTON_ICON_NAME = "Icon";
    public static final Icon BUTTON_ICON_TEST = UIManager.getIcon("FileView.fileIcon");
    public static final String BUTTON_NAME = "TestButton";
    public static final String FRAME_NAME = "Frame";
    public static final String INITIATOR_NAME = "Initiator[" + BUTTON_NAME + "]";

    private static Color buttonBackgroundDefault;
    private static Color buttonForegroundDefault;
    private static Font buttonFontDefault;
    private static String buttonTextDefault;

    private Branch branch;
    private JButtonBBTestApplication frame;
    private FrameFixture window;

    @BeforeAll
    public static void beforeAll() {
        FailOnThreadViolationRepaintManager.install();

        final JButton jb = GuiActionRunner.execute(() -> new JButton());
        final JButtonFixture jbf = new JButtonFixture(BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock(), jb);

        buttonBackgroundDefault = jbf.background().target();
        buttonForegroundDefault = jbf.foreground().target();
        buttonFontDefault = jbf.font().target();
        buttonTextDefault = jbf.text();
    }

    @BeforeEach
    public void beforeEach() {
        branch = new Branch(FRAME_NAME);
        frame = GuiActionRunner.execute(() -> new JButtonBBTestApplication(branch));
        window = new FrameFixture(BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock(), frame);
        window.show();
    }

    @Test
    void testJButtonBB() throws Exception {
        final BasicTransactionQueue basicTransactionQueue = new BasicTransactionQueue();
        final JButtonFixture jButton = window.button(BUTTON_NAME);
        final Root root = Root.builder()
                .setName(this.getClass().getSimpleName())
                .addEventChannel(BUTTON_ACTION_ECD, null)
                .addEventChannel(BUTTON_ENABLED_ECD, Boolean.TRUE)
                .addEventChannel(BUTTON_BACKGROUND_ECD, buttonBackgroundDefault)
                .addEventChannel(BUTTON_FOREGROUND_ECD, buttonForegroundDefault)
                .addEventChannel(BUTTON_FONT_ECD, buttonFontDefault)
                .addEventChannel(BUTTON_TEXT_ECD, buttonTextDefault)
                .addEventChannel(BUTTON_ICON_ECD, BUTTON_ICON_DEFAULT)
                .setTransactionQueue(basicTransactionQueue)
                .build();
        final Initiator initiator = Initiator.builder()
                .setName(INITIATOR_NAME)
                .addEventChannelDescription(BUTTON_ENABLED_ECD)
                .addEventChannelDescription(BUTTON_BACKGROUND_ECD)
                .addEventChannelDescription(BUTTON_FOREGROUND_ECD)
                .addEventChannelDescription(BUTTON_FONT_ECD)
                .addEventChannelDescription(BUTTON_TEXT_ECD)
                .addEventChannelDescription(BUTTON_ICON_ECD)
                .build();
        final TestCommit testCommit = TestCommit.builder()
                .setName(BUTTON_NAME)
                .addTriggeringEcd(BUTTON_ENABLED_ECD)
                .addTriggeringEcd(BUTTON_BACKGROUND_ECD)
                .addTriggeringEcd(BUTTON_FOREGROUND_ECD)
                .addTriggeringEcd(BUTTON_FONT_ECD)
                .addTriggeringEcd(BUTTON_TEXT_ECD)
                .addTriggeringEcd(BUTTON_ICON_ECD)
                .build();
        final TestTriggeredCommit testTriggeredCommit = TestTriggeredCommit.builder()
                .setName(BUTTON_NAME)
                .setTriggeringEcd(BUTTON_ACTION_ECD)
                .build();

        root.add(branch);
        root.add(initiator);
        root.add(testCommit);
        root.add(testTriggeredCommit);

        compareWidgetAndTfwState(jButton, testCommit, 1, basicTransactionQueue);

        assertEquals(0, testTriggeredCommit.getCount());

        window.button(BUTTON_NAME).click();

        compareWidgetAndTfwState(jButton, testCommit, 1, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_BACKGROUND_ECD, BUTTON_BACKGROUND_TEST);

        compareWidgetAndTfwState(jButton, testCommit, 2, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_FOREGROUND_ECD, BUTTON_FOREGROUND_TEST);

        compareWidgetAndTfwState(jButton, testCommit, 3, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_ENABLED_ECD, Boolean.FALSE);

        compareWidgetAndTfwState(jButton, testCommit, 4, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_FONT_ECD, BUTTON_FONT_TEST);

        compareWidgetAndTfwState(jButton, testCommit, 5, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_TEXT_ECD, BUTTON_TEXT_TEST);

        compareWidgetAndTfwState(jButton, testCommit, 6, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());

        initiator.set(BUTTON_ICON_ECD, BUTTON_ICON_TEST);

        compareWidgetAndTfwState(jButton, testCommit, 7, basicTransactionQueue);
        assertEquals(1, testTriggeredCommit.getCount());
    }

    @Test
    void testEmptyJButtonBuilder() {
        final JButtonBB jButtonBB = GuiActionRunner.execute(
                () -> JButtonBB.builder().setName(BUTTON_NAME).build());

        assertEquals(0, new BranchProxy(jButtonBB.getBranch()).getChildProxies().length);

        final TestActionListenerBranchBox testActionListenerBranchBox = new TestActionListenerBranchBox();

        GuiActionRunner.execute(() -> jButtonBB.addActionListenerToBoth(testActionListenerBranchBox));
        GuiActionRunner.execute(() -> jButtonBB.removeActionListenerFromBoth(testActionListenerBranchBox));

        final TestActionListenerTreeComponent testActionListenerTreeComponent =
                new TestActionListenerTreeComponent(BUTTON_ACTION_ECD);

        GuiActionRunner.execute(() -> jButtonBB.addActionListenerToBoth(testActionListenerTreeComponent));
        GuiActionRunner.execute(() -> jButtonBB.removeActionListenerFromBoth(testActionListenerTreeComponent));

        final TestActionListener testActionListener = new TestActionListener();

        assertThrows(
                IllegalArgumentException.class,
                () -> GuiActionRunner.execute(() -> jButtonBB.addActionListenerToBoth(testActionListener)));
        assertThrows(
                IllegalArgumentException.class,
                () -> GuiActionRunner.execute(() -> jButtonBB.removeActionListenerFromBoth(testActionListener)));
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    private static void compareWidgetAndTfwState(
            final JButtonFixture jButtonFixture,
            final TestCommit testCommit,
            final int expectedTestCommitCount,
            final BasicTransactionQueue basicTransactionQueue)
            throws InvocationTargetException, InterruptedException {
        SwingTestUtil.waitForTfwAndSwing(basicTransactionQueue);

        final Color widgetBackground = jButtonFixture.background().target();
        final boolean widgetEnabled = jButtonFixture.isEnabled();
        final Font widgetFont = jButtonFixture.font().target();
        final Color widgetForeground = jButtonFixture.foreground().target();
        final Icon widgetIcon =
                GuiActionRunner.execute(() -> jButtonFixture.target().getIcon());
        final String widgetText = jButtonFixture.text();
        final Map<ObjectECD, Object> tfwState = testCommit.getState();
        final Color tfwBackground = (Color) tfwState.get(BUTTON_BACKGROUND_ECD);
        final boolean tfwEnabled = (Boolean) tfwState.get(BUTTON_ENABLED_ECD);
        final Font tfwFont = (Font) tfwState.get(BUTTON_FONT_ECD);
        final Color tfwForeground = (Color) tfwState.get(BUTTON_FOREGROUND_ECD);
        final Icon tfwIcon = (Icon) tfwState.get(BUTTON_ICON_ECD);
        final String tfwText = (String) tfwState.get(BUTTON_TEXT_ECD);

        assertEquals(expectedTestCommitCount, testCommit.getCount());

        assertEquals(widgetBackground, tfwBackground);
        assertEquals(widgetEnabled, tfwEnabled);
        assertEquals(widgetFont, tfwFont);
        assertEquals(widgetForeground, tfwForeground);
        assertEquals(widgetIcon, tfwIcon);
        assertEquals(widgetText, tfwText);
    }
}
