package tfw.swing.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import tfw.tsm.ecd.StringECD;

@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class SetTextFactoryTest {
    private static final String TEST_NAME = "TestName";
    private static final StringECD TEXT_ECD = new StringECD("TestText");

    private JButton jButton;
    private JTextField jTextField;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        jButton = GuiActionRunner.execute(() -> new JButton());
        jTextField = GuiActionRunner.execute(() -> new JTextField());
    }

    @Test
    void testArguments() {
        assertThrows(IllegalArgumentException.class, () -> SetTextFactory.create(null, TEXT_ECD, jButton, null));
        assertThrows(IllegalArgumentException.class, () -> SetTextFactory.create(TEST_NAME, null, jButton, null));
        assertThrows(
                IllegalArgumentException.class,
                () -> SetTextFactory.create(TEST_NAME, TEXT_ECD, (AbstractButton) null, null));
        assertThrows(IllegalArgumentException.class, () -> SetTextFactory.create(null, TEXT_ECD, jTextField, null));
        assertThrows(IllegalArgumentException.class, () -> SetTextFactory.create(TEST_NAME, null, jTextField, null));
        assertThrows(
                IllegalArgumentException.class,
                () -> SetTextFactory.create(TEST_NAME, TEXT_ECD, (JTextComponent) null, null));
    }
}
